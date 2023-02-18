package com.knet.dormitory.web.shared.exception

import com.knet.dormitory.web.shared.dto.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidException(
        exception: MethodArgumentNotValidException
    ): ResponseEntity<ErrorResponse> = ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(
            ErrorResponse(
                status = HttpStatus.BAD_REQUEST,
                message = exception.bindingResult.allErrors[0].defaultMessage ?: ""
            )
        )

    @ExceptionHandler(IllegalArgumentException::class)
    fun illegalArgumentException(
        exception: IllegalArgumentException
    ): ResponseEntity<ErrorResponse> = ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(
            ErrorResponse(
                status = HttpStatus.BAD_REQUEST,
                message = exception.message ?: ""
            )
        )
}