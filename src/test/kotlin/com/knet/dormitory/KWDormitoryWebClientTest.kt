package com.knet.dormitory

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.knet.dormitory.domain.notice.dto.NoticeRootDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.*
import java.time.LocalDate

@SpringBootTest
@ExtendWith(SpringExtension::class)
class KWDormitoryWebClientTest {

    @Autowired
    private lateinit var webClient: WebClient

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    @ExperimentalCoroutinesApi
    fun `기숙사 공지 사항 내용이 불러와지는 지 확인하는 테스트`() = runTest {
        val res = withContext(Dispatchers.IO) {
            webClient.post()
                .uri("https://kw.happydorm.or.kr/bbs/getBbsList.kmc")
                .accept(MediaType.APPLICATION_JSON, MediaType.TEXT_HTML)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(
                    BodyInserters
                        .fromMultipartData("cPage", 1)
                        .with("rows", 10)
                        .with("bbs_locgbn", "KW")
                        .with("bbs_id", "notice")
                ).retrieve()
                .onStatus({ status -> status.value() != 200 }, { null }) //  200이 아닌 경우 null 반환
                .toEntity(String::class.java)
                .onErrorContinue { ex, o -> ex.printStackTrace() }
                .block()
        }

        val body = res?.body
        val noticeRootDTO = objectMapper.readValue(body, NoticeRootDTO::class.java)
        println(noticeRootDTO)
    }
}

