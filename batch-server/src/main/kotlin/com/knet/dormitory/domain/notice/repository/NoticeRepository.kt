package com.knet.dormitory.domain.notice.repository

import com.knet.dormitory.domain.notice.Notice
import com.knet.dormitory.domain.notice.NoticeId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface NoticeRepository : JpaRepository<Notice, String> {
    fun findByInfoTitle(title: String): Notice?
    fun existsByInfoTitle(title: String): Boolean
}