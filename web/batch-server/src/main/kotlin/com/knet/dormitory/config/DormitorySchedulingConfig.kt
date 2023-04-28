package com.knet.dormitory.config

import com.knet.dormitory.domain.alarm.AlarmService
import com.knet.dormitory.domain.alarm.AlarmTopic
import com.knet.dormitory.domain.notice.entity.NoticeTopic
import com.knet.dormitory.domain.notice.repository.NoticeRepository
import com.knet.dormitory.domain.notice.service.NoticeService
import kotlinx.coroutines.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.transaction.PlatformTransactionManager
import java.util.*

@EnableScheduling
@Configuration
@EnableBatchProcessing
class DormitorySchedulingConfig(
    private val noticeService: NoticeService,
    private val noticeRepository: NoticeRepository,
    private val jobLauncher: JobLauncher,
    private val jobRepository: JobRepository,
    private val alarmService: AlarmService,
    private val transactionManager: PlatformTransactionManager,
) {
    private val RECRUIT = "모집"
    private val logger = getLogger<DormitorySchedulingConfig>()

    @Bean
    fun monitoringJob(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager
    ): Job = job(jobRepository) {
        tasklet(jobRepository, transactionManager) { _, _ ->
            val dtos = noticeService.getNoticeList(page = 1, size = 20)
                .filter { notice -> !noticeRepository.existsByInfoTitle(notice.title) }

            val recruit = dtos.filter { notice -> RECRUIT in notice.title }.map { dto ->
                dto.toEntity().let { notice ->
                    notice.id.generate()
                    notice.changeTopic(NoticeTopic.KW_DORM_RECRUITMENT)
                    notice
                }
            }
            val common = dtos.filter { notice -> RECRUIT !in notice.title }.map { dto ->
                dto.toEntity().let { notice ->
                    notice.id.generate()
                    notice.changeTopic(NoticeTopic.KW_DORM_COMMON)
                    notice
                }
            }
            val notices = recruit + common
            noticeRepository.saveAll(notices)
            try {
                runBlocking(Dispatchers.IO) {
                    withTimeout(2000L) {
                        val deferreds = mutableListOf<Deferred<Int>>()
                        notices.forEachIndexed { i, notice ->
                            val deferred = async {
                                val topic = convertNoticeTopicToAlarmTopic(notice.topic)
                                alarmService.sendMessage(
                                    title = topic.message,
                                    body = notice.info.title,
                                    topic = topic
                                )
                                i
                            }
                            deferreds.add(deferred)
                        }
                        for (deferred in deferreds) deferred.await()
                    }
                }
            } catch (e: Exception) {
                logger.error("send message error : ${e.message}")
            }
            return@tasklet RepeatStatus.FINISHED
        }
    }

    @Scheduled(cron = "1 * * * * *")
    fun dormScheduling() {
        val param = JobParametersBuilder()
            .addDate("date", Date())
            .toJobParameters()
        jobLauncher.run(monitoringJob(jobRepository, transactionManager), param)
    }

    fun convertAlarmToNoticeTopic(topic: AlarmTopic): NoticeTopic = when (topic) {
        AlarmTopic.KW_DORM_COMMON -> NoticeTopic.KW_DORM_COMMON
        AlarmTopic.KW_DORM_RECRUITMENT -> NoticeTopic.KW_DORM_RECRUITMENT
        else -> NoticeTopic.KW_DORM_COMMON
    }

    fun convertNoticeTopicToAlarmTopic(topic: NoticeTopic): AlarmTopic = when (topic) {
        NoticeTopic.KW_DORM_COMMON -> AlarmTopic.KW_DORM_COMMON
        NoticeTopic.KW_DORM_RECRUITMENT -> AlarmTopic.KW_DORM_RECRUITMENT
        else -> AlarmTopic.KW_DORM_COMMON
    }
}

private fun tasklet(
    jobRepository: JobRepository,
    transactionManager: PlatformTransactionManager,
    tasklet: Tasklet
): Step = StepBuilder("dormitory notice monitoring", jobRepository).tasklet(tasklet, transactionManager).build()

private fun job(jobRepository: JobRepository, init: () -> Step): Job =
    JobBuilder("dormitory notice list job", jobRepository)
        .start(init()).build()

inline fun <reified T> getLogger(): Logger = LoggerFactory.getLogger(T::class.java)