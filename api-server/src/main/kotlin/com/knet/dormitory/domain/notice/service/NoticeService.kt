package com.knet.dormitory.domain.notice.service

import com.knet.dormitory.domain.notice.Notice
import com.knet.dormitory.domain.notice.repository.NoticeRepository
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service

@Service
class NoticeService(
    private val noticeRepository: NoticeRepository
) {
    suspend fun getNoticeList(): List<Notice> = noticeRepository
        .findAll()
        .collectList()
        .awaitSingleOrNull()
        ?: throw IllegalStateException("notice list 값을 불러오는 도중에 문제가 발생하였습니다.")
}