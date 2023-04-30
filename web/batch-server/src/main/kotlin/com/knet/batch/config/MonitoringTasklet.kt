package com.knet.batch.config

import com.knet.dormitory.domain.alarm.AlarmService
import com.knet.dormitory.domain.alarm.AlarmTopic
import com.knet.dormitory.domain.notice.entity.NoticeTopic
import com.knet.dormitory.domain.notice.service.NoticeService
import kotlinx.coroutines.*
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component

@Component
class MonitoringTasklet(
    private val noticeService: NoticeService,
    private val alarmService: AlarmService,
) : Tasklet {
    private val RECRUIT = "모집"
    private val logger = getLogger<DormitorySchedulingConfig>()

    private fun convertAlarmToNoticeTopic(topic: AlarmTopic): NoticeTopic = when (topic) {
        AlarmTopic.KW_DORM_COMMON -> NoticeTopic.KW_DORM_COMMON
        AlarmTopic.KW_DORM_RECRUITMENT -> NoticeTopic.KW_DORM_RECRUITMENT
        else -> NoticeTopic.KW_DORM_COMMON
    }

    private fun convertNoticeTopicToAlarmTopic(topic: NoticeTopic): AlarmTopic = when (topic) {
        NoticeTopic.KW_DORM_COMMON -> AlarmTopic.KW_DORM_COMMON
        NoticeTopic.KW_DORM_RECRUITMENT -> AlarmTopic.KW_DORM_RECRUITMENT
        else -> AlarmTopic.KW_DORM_COMMON
    }

    override fun execute(contribution: StepContribution, chunk: ChunkContext): RepeatStatus = filteringAndSendMessage()

    fun filteringAndSendMessage(): RepeatStatus {
        val dtos = noticeService.getNoticeList(page = 1, size = 20)
            .filter { notice -> !noticeService.isExistNoticeBy(title = notice.title) }

        val recruit = dtos
            .filter { notice -> RECRUIT in notice.title }
            .map { dto -> dto.toCreateDTO(NoticeTopic.KW_DORM_RECRUITMENT) }

        val common = dtos
            .filter { notice -> RECRUIT !in notice.title }
            .map { dto -> dto.toCreateDTO(NoticeTopic.KW_DORM_COMMON) }

        val notices = recruit + common
        noticeService.createNoticeAll(notices)
        try {
            runBlocking(Dispatchers.IO) {
                withTimeout(2000L) {
                    val deferreds = mutableListOf<Deferred<Unit>>()
                    notices.forEach { notice ->
                        val deferred = async {
                            val topic = convertNoticeTopicToAlarmTopic(notice.topic)
                            alarmService.sendMessage(
                                title = topic.message,
                                body = notice.title,
                                topic = topic
                            )
                        }
                        deferreds.add(deferred)
                    }
                    for (deferred in deferreds) deferred.await()
                }
            }
        } catch (e: Exception) {
            logger.error("send message error : ${e.message}")
        }
        return RepeatStatus.FINISHED
    }
}