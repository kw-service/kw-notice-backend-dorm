package com.knet.dormitory.domain.notice.repository

import com.knet.dormitory.domain.notice.Notice
import org.springframework.data.jpa.repository.JpaRepository

interface NoticeRepository : JpaRepository<Notice, String> {
    fun findByInfoTitle(title: String): Notice?
    fun existsByInfoTitle(title: String): Boolean
}