package com.knet.mvc.web.shared.dto

import org.springframework.http.HttpStatus

class ErrorResponse(
    val status:HttpStatus,
    val message:String
)