package com.knet.dormitory.domain.notice.repository

import com.knet.dormitory.domain.notice.entity.Notice
import com.knet.dormitory.domain.notice.entity.NoticeId
import org.springframework.data.jpa.repository.JpaRepository

interface NoticeRepository : JpaRepository<Notice, NoticeId> {
    fun existsByInfoTitle(title: String): Boolean
    fun findAllByOrderByInfoCreatedAtAsc(): List<Notice>
}