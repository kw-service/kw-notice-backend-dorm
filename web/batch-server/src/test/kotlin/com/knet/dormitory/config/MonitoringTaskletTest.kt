package com.knet.dormitory.config

import com.knet.dormitory.domain.alarm.AlarmService
import com.knet.dormitory.domain.notice.dto.NoticeShortDTO
import com.knet.dormitory.domain.notice.entity.*
import com.knet.dormitory.domain.notice.service.NoticeService
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.*
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
@SpringBootTest
class MonitoringTaskletTest(
    @MockkBean
    private val noticeService: NoticeService,
    @MockkBean
    private val alarmService: AlarmService,
    private val tasklet: MonitoringTasklet
) : BehaviorSpec({

    given("20개의 공지사항이 있을 때") {
        val shorts = mutableListOf<NoticeShortDTO>()

        for (i in 0 until 20) {
            val short = NoticeShortDTO(
                createdAt = LocalDate.of(2022, 10, i + 1),
                title = "title$i",
                writerId = "writerId$i",
                writerName = "writerName$i",
            )
            shorts.add(short)
        }

        `when`("새로운 공지사항이 3개 포함되어 있으면") {
            every { noticeService.getNoticeList(page = 1, size = 20) } returns shorts
            for (i in 0 until 17)
                every { noticeService.isExistNoticeBy("title$i") } returns true
            for (i in 17 until 20)
                every { noticeService.isExistNoticeBy("title$i") } returns false
            every { noticeService.createNoticeAll(any<List<Notice>>()) } returns emptyList()
            coEvery { alarmService.sendMessage(any(), any(), any()) } returns Unit

            tasklet.filteringAndSendMessage()

            then("3개의 알림을 보낸다.") {
                coVerify(exactly = 3) { alarmService.sendMessage(any(), any(), any()) }
                verify(exactly = 20) { noticeService.isExistNoticeBy(any()) }
                verify(exactly = 1) { noticeService.getNoticeList(any(), any()) }
            }
        }
    }
})