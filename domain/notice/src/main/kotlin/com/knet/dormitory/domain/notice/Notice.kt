package com.knet.dormitory.domain.notice

import jakarta.persistence.*
import org.springframework.data.annotation.Id
import java.io.Serializable
import java.time.LocalDate
import java.util.*


@Entity
@Table(name = "notice")
class Notice(
    id: String? = null,
    title: String,
    writerName: String,
    writerId: String,
    topic: NoticeTopic = NoticeTopic.KW_DORM_COMMON,
    createdAt: LocalDate
) {
    @EmbeddedId
    var id: NoticeId = NoticeId(id)
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
class NoticeId(value: String? = null) : Serializable {
    @Id
    @Column(name = "notice_id")
    var value: String? = value
        protected set

    // @EmbeddedId는 자동생성 기능을 지원하지 않기 때문에 직접 업데이트를 해줘야합니다.
    fun generate(): String = UUID.randomUUID().toString().also { this.value = it }

    override fun toString(): String {
        return "NoticeId(value=$value)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as NoticeId
        if (value != other.value) return false
        return true
    }

    override fun hashCode(): Int = value?.hashCode() ?: 0
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

enum class NoticeTopic(val value: String) {
    KW_DORM_COMMON("kw-dorm-common"), KW_DORM_RECRUITMENT("kw-dorm-recruitment");
}