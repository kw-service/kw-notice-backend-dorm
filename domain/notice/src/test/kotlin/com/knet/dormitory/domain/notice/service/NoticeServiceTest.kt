package com.knet.dormitory.domain.notice.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.knet.dormitory.domain.notice.entity.Notice
import com.knet.dormitory.domain.notice.entity.NoticeTopic
import com.knet.dormitory.domain.notice.repository.NoticeRepository
import com.knet.dormitory.domain.provider.NoticeIdentityProvider
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.web.reactive.function.client.WebClient
import java.time.LocalDate

class NoticeServiceTest : BehaviorSpec({
    val noticeRepository = mockk<NoticeRepository>()
    val provider = mockk<NoticeIdentityProvider>()
    val mapper = ObjectMapper()
    mapper.registerModule(
        KotlinModule.Builder()
            .withReflectionCacheSize(512)
            .configure(KotlinFeature.NullToEmptyCollection, false)
            .configure(KotlinFeature.NullToEmptyMap, false)
            .configure(KotlinFeature.NullIsSameAsDefault, false)
            .configure(KotlinFeature.SingletonSupport, false)
            .configure(KotlinFeature.StrictNullChecks, false)
            .build()
    )
    mapper.registerModule(JavaTimeModule())
    val client = WebClient.create()
    val noticeService = NoticeService(
        noticeRepository = noticeRepository,
        objectMapper = mapper,
        webClient = client,
        provider = provider
    )

    given("공지사항이 20개 있을때") {
        val notices = mutableListOf<Notice>()

        for (i in 0..9) {
            val notice = Notice(
                id = "id$i",
                title = "title$i",
                topic = NoticeTopic.KW_DORM_COMMON,
                writerId = "writerId$i",
                writerName = "writerName$i",
                createdAt = LocalDate.now(),
            )
            notices.add(notice)
        }

        for (i in 10..19) {
            val notice = Notice(
                id = "id$i",
                title = "title$i",
                topic = NoticeTopic.KW_DORM_RECRUITMENT,
                writerId = "writerId$i",
                writerName = "writerName$i",
                createdAt = LocalDate.of(2022, 10, i + 1),
            )
            notices.add(notice)
        }

        every { noticeRepository.findAllByOrderByInfoCreatedAtAsc() } returns notices

        `when`("공지사항 전체 조회를 하면") {
            val details = noticeService.findAll()

            then("20개의 공지사항이 조회되어야한다.") {
                for (i in 0..19) {
                    details[i].noticeId shouldBe notices[i].id
                    details[i].writer.id shouldBe notices[i].writer.id
                    details[i].writer.name shouldBe notices[i].writer.name
                    details[i].topic shouldBe notices[i].topic
                    details[i].info.title shouldBe notices[i].info.title
                    details[i].info.createdAt shouldBe notices[i].info.createdAt
                }
            }
        }
    }

    given("기숙사 공지사항이 있을 때") {
        `when`("기숙사 공지사항을 20개 조회하면") {
            val notices = noticeService.getNoticeList(1, 20)
            then("기숙사 공지사항이 20개 실제로 조회되어야한다.") {
                notices.size shouldBe 20
            }
        }
        `when`("공지사항 개수를 조회하면") {
            val size = noticeService.getNoticeTotalCount()
            then("전체 공지사항의 개수가 출력되어야한다.") {
                size shouldNotBe null
                size shouldNotBe 0
            }
        }
    }
})