package com.knet.dormitory.domain.notice.dto

import com.knet.dormitory.domain.notice.Notice
import java.time.LocalDate


class NoticeResponseDTO(
    val noticeId: String,
    val title: String,
    val writerName: String,
    val writerId: String,
    val createdAt: LocalDate
) {
    companion object {
        fun from(notice: Notice): NoticeResponseDTO {
            return NoticeResponseDTO(
                noticeId = notice.id.uuid!!,
                writerId = notice.writer.id,
                writerName = notice.writer.name,
                createdAt = notice.info.createdAt,
                title = notice.info.title
            )
        }
    }
}