package com.knet.dormitory.domain.notice.repository

import com.knet.dormitory.domain.notice.Notice
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface NoticeRepository : R2dbcRepository<Notice, String>, CustomNoticeRepositoryDSL {
    @Query(value = "select * from notice where title=:title limit 1")
    suspend fun findByTitle(title : String) : Mono<Notice?>
}