package com.knet.dormitory.presentation

import com.knet.dormitory.domain.notice.dto.NoticeResponseDTO
import com.knet.dormitory.domain.notice.service.NoticeService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class NoticeHandler(private val noticeService: NoticeService) {

    suspend fun getNoticeList(): ServerResponse =
        ServerResponse.ok()
            .bodyValueAndAwait(
                noticeService.getNoticeList()
                    .map(NoticeResponseDTO.Companion::from)
            )
}