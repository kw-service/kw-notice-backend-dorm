package com.knet.dormitory.domain.notice.dto

import com.knet.dormitory.domain.notice.entity.Notice
import com.knet.dormitory.domain.notice.entity.NoticeTopic
import java.time.LocalDate

class NoticeCreateDTO(
    val title: String,
    val writerName: String,
    val writerId: String,
    val createdAt: LocalDate,
    val topic : NoticeTopic
) {
    fun toEntity(id: String): Notice = Notice(
        id = id,
        title = title,
        writerName = writerName,
        writerId = writerId,
        createdAt = createdAt,
        topic = topic
    )
}