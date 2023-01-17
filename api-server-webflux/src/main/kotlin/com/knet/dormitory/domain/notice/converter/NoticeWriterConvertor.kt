package com.knet.dormitory.domain.notice.converter

import com.knet.dormitory.domain.notice.Notice
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.r2dbc.mapping.OutboundRow
import org.springframework.r2dbc.core.Parameter.from

@WritingConverter
class NoticeWriterConvertor : Converter<Notice, OutboundRow> {
    override fun convert(source: Notice): OutboundRow {
        val row = OutboundRow()
        row.put("notice_id", from(source.id.uuid ?: source.id.generate()))
        row.put("title", from(source.info.title))
        row.put("writer_name", from(source.writer.name))
        row.put("writer_id", from(source.writer.id))
        row.put("created_at", from(source.info.createdAt))
        return row
    }
}