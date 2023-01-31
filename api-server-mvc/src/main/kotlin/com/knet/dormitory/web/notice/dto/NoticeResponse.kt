package com.knet.dormitory.web.notice.dto

import com.knet.dormitory.domain.notice.Notice
import com.knet.dormitory.domain.notice.NoticeTopic
import com.knet.dormitory.domain.notice.dto.NoticeDetailDTO
import java.time.LocalDate


class NoticeResponse(
    val id: Long,
    val title: String,
    val writerId: String,
    val writerName: String,
    val topic: NoticeTopic,
    val url: String,
    val createdAt: LocalDate
) {
    companion object {
        fun from(dto: NoticeDetailDTO, id:Long): NoticeResponse = NoticeResponse(
            id = id,
            title = dto.info.title,
            createdAt = dto.info.createdAt,
            writerId = dto.writer.id,
            writerName = dto.writer.name,
            topic = dto.topic,
            url = dto.url
        )
    }

    override fun toString(): String =
        "NoticeResponse(title='$title', writerId='$writerId', writerName='$writerName', createdAt=${createdAt.atStartOfDay()})"
}