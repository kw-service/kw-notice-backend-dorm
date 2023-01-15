package com.knet.dormitory.domain.notice.service

import com.knet.dormitory.domain.notice.dto.NoticeShortDTO

interface NoticeService {
    fun getNoticeList(page:Int, size:Int) : List<NoticeShortDTO>?
}