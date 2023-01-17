package com.knet.dormitory.web.notice

import com.knet.dormitory.domain.notice.service.NoticeService
import com.knet.dormitory.web.notice.dto.NoticeResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/notice")
class NoticeController(
    private val noticeService: NoticeService
) {
    @GetMapping("")
    fun findAll(): List<NoticeResponse> = noticeService.findAll().map { NoticeResponse.from(it) }
}