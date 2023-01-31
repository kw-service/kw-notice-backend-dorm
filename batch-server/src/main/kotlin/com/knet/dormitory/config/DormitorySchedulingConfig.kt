package com.knet.dormitory.config

import com.knet.dormitory.domain.alarm.AlarmService
import com.knet.dormitory.domain.alarm.AlarmTopic
import com.knet.dormitory.domain.notice.NoticeTopic
import com.knet.dormitory.domain.notice.repository.NoticeRepository
import com.knet.dormitory.domain.notice.service.NoticeService
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
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
    private val jpaTransactionManager: PlatformTransactionManager,
    private val alarmService: AlarmService
) {
    private val logger = LoggerFactory.getLogger(DormitorySchedulingConfig::class.java)

    @Bean
    fun dormitoryNoticeMonitoringJob(
        jobRepository: JobRepository,
        jpaTransactionManager: PlatformTransactionManager
    ): Job {
        val step = StepBuilder("test", jobRepository)
            .tasklet({ _, _ ->
                val notices = noticeService.getNoticeList(page = 1, size = noticeService.getNoticeTotalCount() ?: 0)
                    ?: return@tasklet RepeatStatus.CONTINUABLE


                notices
                    .reversed()
                    .filter { notice -> !noticeRepository.existsByInfoTitle(notice.title) }
                    .groupBy { notice ->
                        when {
                            "모집" in notice.title -> AlarmTopic.KW_DORM_RECRUITMENT
                            else -> AlarmTopic.KW_DORM_COMMON
                        }
                    }.forEach {
                        logger.info(it.toString())
                        it.value.map { dto ->
                            val entity = dto.toEntity()
                            entity.changeTopic(convertAlarmToNoticeTopic(it.key))
                            return@map entity
                        }.forEach { notice ->
                            val id = noticeRepository.count()+1
                            notice.id.generate(id)
                            noticeRepository.save(notice) // 값을 저장
                            alarmService.sendMessage(
                                "새로운 공지사항이 올라왔습니다.",
                                notice.info.title,
                                it.key
                            )// 새로운 공지사항이 올라왔다고 알림
                        }
                    }
                return@tasklet RepeatStatus.FINISHED
            }, jpaTransactionManager)
            .build()

        return JobBuilder("dormitory notice list job", jobRepository)
            .start(step)
            .build()
    }

    /**
     * execute job per 1 min for monitoring dormitory notice
     * @author tianea
     */
    @Scheduled(cron = "1 * * * * *")
    fun dormScheduling() {
        val param = JobParametersBuilder()
            .addDate("date", Date())
            .toJobParameters()
        jobLauncher.run(dormitoryNoticeMonitoringJob(jobRepository, jpaTransactionManager), param)
    }

    fun convertAlarmToNoticeTopic(topic: AlarmTopic): NoticeTopic = when (topic) {
        AlarmTopic.KW_DORM_COMMON -> NoticeTopic.KW_DORM_COMMON
        AlarmTopic.KW_DORM_RECRUITMENT -> NoticeTopic.KW_DORM_RECRUITMENT
        else -> NoticeTopic.KW_DORM_COMMON
    }
}
