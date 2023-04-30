package com.knet.dormitory.domain.notice.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.knet.dormitory.domain.notice.dto.NoticeCreateDTO
import com.knet.dormitory.domain.notice.dto.NoticeDetailDTO
import com.knet.dormitory.domain.notice.dto.NoticeRootDTO
import com.knet.dormitory.domain.notice.dto.NoticeShortDTO
import com.knet.dormitory.domain.notice.entity.Notice
import com.knet.dormitory.domain.notice.repository.NoticeRepository
import com.knet.dormitory.domain.provider.NoticeIdentityProvider
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient

@Service
class NoticeService(
    private val webClient: WebClient,
    private val objectMapper: ObjectMapper,
    private val provider: NoticeIdentityProvider,
    private val noticeRepository: NoticeRepository
) {
    private val DORMITORY_BASE_URL = "https://kw.happydorm.or.kr"

    fun getNoticeList(page: Int, size: Int): List<NoticeShortDTO> {
        val response = webClient.post()
            .uri("${DORMITORY_BASE_URL}/bbs/getBbsList.kmc")
            .accept(MediaType.APPLICATION_FORM_URLENCODED)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(
                BodyInserters.fromMultipartData("cPage", page)
                    .with("rows", size)
                    .with("bbs_locgbn", "KW")
                    .with("bbs_id", "notice")
            ).retrieve()
            .toEntity(String::class.java)
            .block()

        val body = response?.body ?: return emptyList()
        val dto = objectMapper.readValue<NoticeRootDTO>(body)
        return dto.root?.get(0)?.list ?: emptyList()
    }

    fun getNoticeTotalCount(): Int? {
        val response = webClient.post()
            .uri("${DORMITORY_BASE_URL}/bbs/getBbsList.kmc")
            .accept(MediaType.APPLICATION_FORM_URLENCODED)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(
                BodyInserters.fromMultipartData("cPage", 1)
                    .with("rows", 10)
                    .with("bbs_locgbn", "KW")
                    .with("bbs_id", "notice")
            ).retrieve()
            .toEntity(String::class.java)
            .block()

        val body = response?.body ?: return null
        val dto = objectMapper.readValue(body, NoticeRootDTO::class.java)
        return dto.root?.get(0)?.totalCount?.get(0)?.count
    }

    fun findAll(): List<NoticeDetailDTO> =
        noticeRepository.findAllByOrderByInfoCreatedAtAsc()
            .map { notice -> NoticeDetailDTO.from(notice, DORMITORY_BASE_URL) }

    fun createNoticeAll(notices: List<NoticeCreateDTO>): List<Notice> = noticeRepository.saveAll(notices.map { dto ->
        dto.toEntity(id = provider.generateId())
    })

    fun isExistNoticeBy(title: String) = noticeRepository.existsByInfoTitle(title)
}