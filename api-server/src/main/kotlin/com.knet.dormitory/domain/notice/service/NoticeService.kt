package com.knet.dormitory.domain.notice.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.knet.dormitory.domain.notice.Notice
import com.knet.dormitory.domain.notice.dto.NoticeRootDTO
import com.knet.dormitory.domain.notice.dto.NoticeShortDTO
import com.knet.dormitory.domain.notice.repository.NoticeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.withContext
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient

@Service
class NoticeService(
    private val webClient: WebClient,
    private val objectMapper: ObjectMapper,
    private val noticeRepository: NoticeRepository
) {
    suspend fun getNoticeList(): List<Notice> = noticeRepository
        .findAll()
        .collectList()
        .awaitSingleOrNull()
        ?: throw IllegalStateException("notice list 값을 불러오는 도중에 문제가 발생하였습니다.")

//    suspend fun getNoticeList(): List<NoticeShortDTO> = withContext(Dispatchers.IO) {
//        val res = webClient.post()
//            .uri("https://kw.happydorm.or.kr/bbs/getBbsList.kmc")
//            .accept(MediaType.APPLICATION_JSON, MediaType.TEXT_HTML)
//            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//            .body(
//                BodyInserters
//                    .fromMultipartData("cPage", 1)
//                    .with("rows", 10)
//                    .with("bbs_locgbn", "KW")
//                    .with("bbs_id", "notice")
//            ).retrieve()
//            .onStatus({ status -> status.value() != 200 }, { null }) //  200이 아닌 경우 null 반환
//            .toEntity(String::class.java)
//            .onErrorContinue { ex, o -> ex.printStackTrace() }
//            .block()
//
//        val body = res?.body
//        val noticeRootDTO = objectMapper.readValue(body, NoticeRootDTO::class.java)
//        noticeRootDTO.root?.get(0)?.list ?: throw IllegalStateException("기숙사 서버로부터 값을 불러오는 것에 실패하였습니다.")
//    }

}