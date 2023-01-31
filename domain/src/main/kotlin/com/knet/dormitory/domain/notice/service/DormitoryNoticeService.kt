package com.knet.dormitory.domain.notice.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.knet.dormitory.domain.notice.Notice
import com.knet.dormitory.domain.notice.dto.NoticeDetailDTO
import com.knet.dormitory.domain.notice.dto.NoticeRootDTO
import com.knet.dormitory.domain.notice.dto.NoticeShortDTO
import com.knet.dormitory.domain.notice.repository.NoticeRepository
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient

@Service
class DormitoryNoticeService(
    private val webClient: WebClient,
    private val objectMapper: ObjectMapper,
    private val noticeRepository: NoticeRepository
) : NoticeService {
    private val DORMITORY_BASE_URL = "https://kw.happydorm.or.kr"

    /**
     * recall to dormitory notice list
     * @param page page number to recall
     * @param size number of rows of recall
     * @author tianea
     */
    override fun getNoticeList(page: Int, size: Int): List<NoticeShortDTO>? {
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

        val body = response?.body ?: return null
        val dto = objectMapper.readValue(body, NoticeRootDTO::class.java)
        return dto.root?.get(0)?.list
    }

    /**
     * load current dormitory notice count
     * @return when get count success return count, else return null
     */
    override fun getNoticeTotalCount(): Int? {
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

    override fun findAll(): List<NoticeDetailDTO> = noticeRepository.findAll().map { notice -> NoticeDetailDTO.from(notice, DORMITORY_BASE_URL)}
}