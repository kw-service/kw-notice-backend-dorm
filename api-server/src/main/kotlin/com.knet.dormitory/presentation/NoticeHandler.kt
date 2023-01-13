package com.knet.dormitory.presentation

import com.knet.dormitory.domain.notice.dto.NoticeResponseDTO
import com.knet.dormitory.domain.notice.service.NoticeService
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerResponse

@Component
class NoticeHandler(private val noticeService: NoticeService) {

    suspend fun getNoticeList(): ServerResponse =
        ServerResponse
            .ok()
            .bodyValue(noticeService.getNoticeList().map { NoticeResponseDTO.from(it) })
            .awaitSingle()
}