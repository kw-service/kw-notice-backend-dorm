package com.knet.dormitory.domain.notice.service

import com.knet.dormitory.domain.notice.dto.NoticeDetailDTO
import com.knet.dormitory.domain.notice.dto.NoticeShortDTO

interface NoticeService {
    fun getNoticeList(page:Int, size:Int) : List<NoticeShortDTO>?
    fun getNoticeTotalCount() : Int?
    fun findAll() : List<NoticeDetailDTO>
}