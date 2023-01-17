package com.knet.dormitory.web.notice.dto

import com.knet.dormitory.domain.notice.Notice
import com.knet.dormitory.domain.notice.NoticeTopic
import java.time.LocalDate


class NoticeResponse(
    val title: String,
    val writerId: String,
    val writerName: String,
    val topic : NoticeTopic,
    val createdAt: LocalDate
) {
    companion object {
        fun from(notice: Notice): NoticeResponse = NoticeResponse(
            title = notice.info.title,
            writerId = notice.writer.id,
            writerName = notice.writer.name,
            topic = notice.topic,
            createdAt = notice.info.createdAt
        )
    }

    override fun toString(): String = "NoticeResponse(title='$title', writerId='$writerId', writerName='$writerName', createdAt=${createdAt.atStartOfDay()})"
}