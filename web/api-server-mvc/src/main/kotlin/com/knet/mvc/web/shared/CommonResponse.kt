package com.knet.mvc.web.shared

import org.springframework.http.HttpStatus

class CommonResponse(
    val status: HttpStatus,
    val message:String
) {
    override fun toString(): String = "CommonResponse(status=$status, message='$message')"
}