package com.knet.dormitory.web.notice

import com.knet.dormitory.domain.notice.service.NoticeService
import com.knet.dormitory.web.notice.dto.NoticeResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "notice")
@RestController
@RequestMapping("/notice")
class NoticeController(
    private val noticeService: NoticeService
) {
    @GetMapping("")
    @Operation(description = "기숙사 공지사항 리스트 조회 전체 조회")
    @ApiResponse(responseCode = "200", description = "성공 시")
    fun findAll(): List<NoticeResponse> =
        noticeService.findAll().mapIndexed { index, dto -> NoticeResponse.from(dto, index.toLong() + 1) }
}