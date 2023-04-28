package com.knet.dormitory.domain.notice.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.knet.dormitory.domain.notice.entity.Notice
import com.knet.dormitory.domain.notice.entity.NoticeTopic
import com.knet.dormitory.domain.notice.repository.NoticeRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.springframework.web.reactive.function.client.WebClient
import java.time.LocalDate

class KotestMockkNoticeServiceTest : BehaviorSpec({
    val webClient: WebClient = mockk()
    val objectMapper: ObjectMapper = mockk()
    val noticeRepository: NoticeRepository = mockk()

    given("10개의 notice가 존재할 때") {
        val testNotices = arrayListOf<Notice>()
        for (i in 0 until 10) {
            testNotices.add(
                Notice(
                    title = "title$i",
                    writerName = "writer name$i",
                    writerId = "writer id$i",
                    topic = NoticeTopic.KW_DORM_COMMON,
                    createdAt = LocalDate.now()
                )
            )
        }
        every { noticeRepository.findAllByOrderByInfoCreatedAtAsc() } returns testNotices

        val noticeService = NoticeService(
            webClient = webClient,
            objectMapper = objectMapper,
            noticeRepository = noticeRepository
        )

        `when`("findall로 조회하면") {
            val notices = noticeService.findAll()

            then("10개의 notice가 조회되어야한다.") {
                Assertions.assertTrue { notices.size == 10 }
            }
            then("findAllByOrderByInfoCreatedAtAsc 가 한번만 조회되어야한다.") {
                verify(exactly = 1) { noticeRepository.findAllByOrderByInfoCreatedAtAsc() }
            }
        }
    }
}) {
}