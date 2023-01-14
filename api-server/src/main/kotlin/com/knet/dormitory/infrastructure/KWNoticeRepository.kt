package com.knet.dormitory.infrastructure

import com.knet.dormitory.domain.notice.repository.CustomNoticeRepositoryDSL
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository

@Repository
class KWNoticeRepository(private val client: DatabaseClient) : CustomNoticeRepositoryDSL {

}