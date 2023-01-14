package com.knet.dormitory.shard.dto

import org.springframework.http.HttpStatus

class CommonResponse(val status: HttpStatus, val message: String)
