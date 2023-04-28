package com.knet.dormitory.config

import com.knet.dormitory.domain.alarm.AlarmService
import com.knet.dormitory.domain.alarm.AlarmTopic
import com.knet.dormitory.domain.notice.entity.NoticeTopic
import com.knet.dormitory.domain.notice.repository.NoticeRepository
import com.knet.dormitory.domain.notice.service.NoticeService
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
    private val alarmService: AlarmService
) {
    private val logger = LoggerFactory.getLogger(DormitorySchedulingConfig::class.java)
    private val RECRUIT = "모집"
    @Bean
    fun dormitoryNoticeMonitoringJob(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager
    ): Job = job(jobRepository) {
        tasklet(jobRepository, transactionManager) { _, _ ->
            noticeService.getNoticeList(page = 1, size = 20)
                .reversed()
                .filter { notice -> !noticeRepository.existsByInfoTitle(notice.title) }
                .groupBy { notice ->
                    when (RECRUIT) {
                        in notice.title -> AlarmTopic.KW_DORM_RECRUITMENT
                        else -> AlarmTopic.KW_DORM_COMMON
                    }
                }.forEach {
                    logger.info(it.toString())
                    it.value.map { dto ->
                        val entity = dto.toEntity()
                        entity.changeTopic(convertAlarmToNoticeTopic(it.key))
                        return@map entity
                    }.forEach { notice ->
                        notice.id.generate()
                        noticeRepository.save(notice) // 값을 저장
                        alarmService.sendMessage(
                            it.key.message,
                            notice.info.title,
                            it.key
                        )// 새로운 공지사항이 올라왔다고 알림
                    }
                }
            return@tasklet RepeatStatus.FINISHED
        }
    }

    @Scheduled(cron = "1 * * * * *")
    fun dormScheduling(transactionManager: PlatformTransactionManager) {
        val param = JobParametersBuilder()
            .addDate("date", Date())
            .toJobParameters()
        jobLauncher.run(dormitoryNoticeMonitoringJob(jobRepository, transactionManager), param)
    }

    fun convertAlarmToNoticeTopic(topic: AlarmTopic): NoticeTopic = when (topic) {
        AlarmTopic.KW_DORM_COMMON -> NoticeTopic.KW_DORM_COMMON
        AlarmTopic.KW_DORM_RECRUITMENT -> NoticeTopic.KW_DORM_RECRUITMENT
        else -> NoticeTopic.KW_DORM_COMMON
    }
}

private fun tasklet(
    jobRepository: JobRepository,
    transactionManager: PlatformTransactionManager,
    tasklet: Tasklet
): Step = StepBuilder("test", jobRepository).tasklet(tasklet, transactionManager).build()

private fun job(jobRepository: JobRepository, init: () -> Step): Job =
    JobBuilder("dormitory notice list job", jobRepository)
        .start(init()).build()
