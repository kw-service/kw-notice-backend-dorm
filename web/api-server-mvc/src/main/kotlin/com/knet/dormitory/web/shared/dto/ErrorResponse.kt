package com.knet.dormitory.web.shared.dto

import org.springframework.http.HttpStatus

class ErrorResponse(
    val status:HttpStatus,
    val message:String
)