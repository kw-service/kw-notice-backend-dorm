package com.knet.dormitory.domain.notice.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate


@JsonIgnoreProperties(ignoreUnknown = true)
class NoticeShortDTO(
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("regdate")
    var createdAt: LocalDate = LocalDate.now(),
    @JsonProperty("regid")
    var writerId: String = "default",
    @JsonProperty("regname")
    var writerName: String = "default",
    @JsonProperty("subject")
    var title: String = "default"
) {
    override fun toString(): String {
        return "NoticeShortDTO(createdAt=$createdAt, writerId='$writerId', writerName='$writerName', title='$title')"
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class NoticeListDTO(var list: List<NoticeShortDTO>? = null) {
    override fun toString(): String {
        return "NoticeListDTO(list=$list)"
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class NoticeRootDTO(var root: List<NoticeListDTO>? = null) {
    override fun toString(): String {
        return "NoticeRootDTO(root=$root)"
    }
}