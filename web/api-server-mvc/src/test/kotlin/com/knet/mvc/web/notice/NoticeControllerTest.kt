package com.knet.mvc.web.notice

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.knet.dormitory.domain.notice.dto.NoticeDetailDTO
import com.knet.dormitory.domain.notice.entity.NoticeId
import com.knet.dormitory.domain.notice.entity.NoticeInfo
import com.knet.dormitory.domain.notice.entity.NoticeTopic
import com.knet.dormitory.domain.notice.entity.NoticeWriter
import com.knet.dormitory.domain.notice.service.NoticeService
import com.knet.mvc.web.notice.dto.NoticeResponse
import com.knet.mvc.web.notice.NoticeController
import com.ninjasquad.springmockk.MockkBean
import com.ninjasquad.springmockk.SpykBean
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import org.junit.jupiter.api.Assertions.*
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.time.LocalDate
import java.time.LocalDateTime

@WebMvcTest(NoticeController::class)
class NoticeControllerTest(
    private val mockMvc: MockMvc,
    @MockkBean
    private val noticeService: NoticeService,
    @SpykBean
    private val mapper: ObjectMapper
) : BehaviorSpec({
    given("20개의 공지사항이 있을때") {
        `when`("공지사항 전체 조회를 하면") {
            val details = mutableListOf<NoticeDetailDTO>()
            for (i in 1..10) {
                val detail = NoticeDetailDTO(
                    noticeId = NoticeId("noticeId$i"),
                    writer = NoticeWriter("writerId$i", "writerName$i"),
                    topic = NoticeTopic.KW_DORM_COMMON,
                    info = NoticeInfo("title$i", LocalDate.now()),
                    url = "url$i"
                )
                details.add(detail)
            }
            for (i in 11..20) {
                val detail = NoticeDetailDTO(
                    noticeId = NoticeId("noticeId$i"),
                    writer = NoticeWriter("writerId$i", "writerName$i"),
                    topic = NoticeTopic.KW_DORM_RECRUITMENT,
                    info = NoticeInfo("title$i", LocalDate.now()),
                    url = "url$i"
                )
                details.add(detail)
            }
            every { noticeService.findAll() } returns details

            val action = mockMvc.get("/notice")

            then("20개의 공지사항이 조회되어야한다.") {
                val result = action.andExpect { status { isOk() } }
                    .andReturn()
                val response = mapper.readValue<List<NoticeResponse>>(result.response.contentAsString)

                for(i in 1..10){
                    response[i-1].id shouldBe i.toLong()
                    response[i-1].title shouldBe details[i-1].info.title
                    response[i-1].writerId shouldBe details[i-1].writer.id
                    response[i-1].writerName shouldBe details[i-1].writer.name
                    response[i-1].topic shouldBe details[i-1].topic
                    response[i-1].url shouldBe details[i-1].url
                    response[i-1].createdAt shouldBe details[i-1].info.createdAt
                }

                for(i in 11..20){
                    response[i-1].id shouldBe i.toLong()
                    response[i-1].title shouldBe details[i-1].info.title
                    response[i-1].writerId shouldBe details[i-1].writer.id
                    response[i-1].writerName shouldBe details[i-1].writer.name
                    response[i-1].topic shouldBe details[i-1].topic
                    response[i-1].url shouldBe details[i-1].url
                    response[i-1].createdAt shouldBe details[i-1].info.createdAt
                }
            }
        }
    }
})