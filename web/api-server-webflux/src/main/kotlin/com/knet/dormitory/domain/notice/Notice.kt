package com.knet.dormitory.domain.notice

import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate
import java.util.*

@Table(name = "notice")
class Notice(
    uuid: String? = null,
    title: String,
    writerName: String,
    writerId: String,
    createdAt: LocalDate
) : Persistable<String> {
    @Id
    @Column(value = "notice_id")
    var id: NoticeId = NoticeId(uuid)
        private set

    var writer: NoticeWriter = NoticeWriter(writerId, writerName)
        private set

    var info: NoticeInfo = NoticeInfo(title, createdAt)
    override fun getId(): String? = id.uuid
    override fun isNew(): Boolean = id.uuid == null
    override fun toString(): String = "Notice(id=$id, writer=$writer, info=$info)"
}

class NoticeWriter(
    id: String,
    name: String
) {
    var id: String = id
        private set
    var name: String = name
        private set

    override fun toString(): String = "NoticeWriter(id='$id', name='$name')"
}

class NoticeId(uuid: String? = null) {
    var uuid: String? = uuid
        private set

    fun generate() = UUID.randomUUID().toString().also { uuid = it }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as NoticeId
        if (uuid != other.uuid) return false
        return true
    }

    override fun hashCode(): Int = uuid?.hashCode() ?: 0
    override fun toString(): String = "NoticeId(uuid=$uuid)"
}

class NoticeInfo(title: String, createdAt: LocalDate) {
    var title: String = title
        private set
    var createdAt: LocalDate = createdAt
        private set

    override fun toString(): String = "NoticeInfo(title='$title', createdAt=$createdAt)"
}