package com.knet.dormitory.domain.notice.converter

import com.knet.dormitory.domain.notice.Notice
import io.r2dbc.spi.Row
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import java.time.LocalDate

@ReadingConverter
class NoticeReadConverter : Converter<Row, Notice> {
    override fun convert(source: Row): Notice? {
        val noticeId: String = (source["notice_id"] as String?) ?: return null
        val title: String = (source["title"] as String?) ?: return null
        val writerName: String = (source["writer_name"] as String?) ?: return null
        val writerId: String = (source["writer_id"] as String?) ?: return null
        val createdAt: LocalDate = (source["created_at"] as LocalDate?) ?: return null
        return Notice(
            uuid = noticeId,
            title = title,
            writerName = writerName,
            writerId = writerId,
            createdAt = createdAt
        )
    }
}