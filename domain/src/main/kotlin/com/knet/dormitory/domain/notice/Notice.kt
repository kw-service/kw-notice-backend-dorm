package com.knet.dormitory.domain.notice

import jakarta.persistence.*
import org.springframework.data.annotation.Id
import java.io.Serializable
import java.time.LocalDate
import java.util.*


@Entity
@Table(name = "notice")
class Notice(
    uuid: String? = null,
    title: String,
    writerName: String,
    writerId: String,
    topic: NoticeTopic = NoticeTopic.COMMON,
    createdAt: LocalDate
) {
    @EmbeddedId
    var id: NoticeId = NoticeId(uuid)
        private set

    @Embedded
    var writer: NoticeWriter = NoticeWriter(writerId, writerName)
        private set

    @Embedded
    var info: NoticeInfo = NoticeInfo(title, createdAt)

    @Enumerated(value = EnumType.STRING)
    @Column(name = "topic")
    var topic: NoticeTopic = topic

    fun changeTopic(topic: NoticeTopic) = topic.also { this.topic = it }
    override fun toString(): String = "Notice(id=$id, writer=$writer, info=$info, topic=$topic)"
}

@Embeddable
class NoticeWriter(
    id: String,
    name: String
) {
    @Column(name = "writer_id")
    var id: String = id
        protected set

    @Column(name = "writer_name")
    var name: String = name
        protected set

    override fun toString(): String = "NoticeWriter(id='$id', name='$name')"
}

@Embeddable
class NoticeId(uuid: String? = null) : Serializable {
    @Id
    @Column(name = "notice_id")
    var uuid: String? = uuid
        protected set

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

@Embeddable
class NoticeInfo(title: String, createdAt: LocalDate) {
    @Column(name = "title")
    var title: String = title
        protected set

    @Column(name = "created_at")
    var createdAt: LocalDate = createdAt
        protected set

    override fun toString(): String = "NoticeInfo(title='$title', createdAt=$createdAt)"
}

enum class NoticeTopic {
    COMMON, REFRIGERATOR, REGULAR_RECRUITMENT
}