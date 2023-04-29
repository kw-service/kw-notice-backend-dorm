package com.knet.mvc.web.notice.dto

import com.knet.dormitory.domain.notice.entity.NoticeTopic
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
        fun from(dto: NoticeDetailDTO, id: Long): NoticeResponse = NoticeResponse(
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
        "NoticeResponse(id=$id, title='$title', writerId='$writerId', writerName='$writerName', topic=$topic, url='$url', createdAt=$createdAt)"
}