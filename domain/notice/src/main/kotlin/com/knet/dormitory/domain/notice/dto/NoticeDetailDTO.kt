package com.knet.dormitory.domain.notice.dto

import com.knet.dormitory.domain.notice.entity.*


class NoticeDetailDTO(
    val noticeId: NoticeId,
    val writer: NoticeWriter,
    val topic: NoticeTopic,
    val info : NoticeInfo,
    val url: String,
) {
    companion object {
        fun from(notice: Notice, url: String): NoticeDetailDTO = NoticeDetailDTO(
            noticeId = notice.id,
            writer = notice.writer,
            topic = notice.topic,
            info = notice.info,
            url = url
        )
    }
}