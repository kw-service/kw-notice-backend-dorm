package com.knet.dormitory.domain.notice.repository

import com.knet.dormitory.domain.notice.Notice
import com.knet.dormitory.domain.notice.NoticeId
import org.springframework.data.jpa.repository.JpaRepository

interface NoticeRepository : JpaRepository<Notice, NoticeId> {
    fun findByInfoTitle(title: String): Notice?
    fun existsByInfoTitle(title: String): Boolean
}