package com.knet.dormitory.web.notice.exception

import com.knet.dormitory.domain.notice.error.NoticeNotFoundException
import com.knet.dormitory.web.shared.dto.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class NoticeExceptionHandler {

    @ExceptionHandler(NoticeNotFoundException::class)
    fun noticeNotFoundException(exception: NoticeNotFoundException): ResponseEntity<ErrorResponse> =
        ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(
                ErrorResponse(
                    status = HttpStatus.NOT_FOUND,
                    message = exception.message ?: ""
                )
            )
}