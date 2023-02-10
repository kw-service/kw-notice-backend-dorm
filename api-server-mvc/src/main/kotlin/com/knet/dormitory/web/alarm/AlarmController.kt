package com.knet.dormitory.web.alarm

import com.knet.dormitory.domain.alarm.AlarmService
import com.knet.dormitory.web.alarm.dto.SendMessageRequest
import com.knet.dormitory.web.shared.CommonResponse
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/alarm")
class AlarmController(
    private val alarmService: AlarmService
) {
    @PostMapping("/send")
    fun sendMessage(@Validated @RequestBody dto: SendMessageRequest): CommonResponse {
        val title = dto.title ?: throw IllegalArgumentException("title is null")
        val body = dto.body ?: throw IllegalArgumentException("body is null")
        val topic = dto.topic ?: throw IllegalArgumentException("topic is null")
        alarmService.sendMessage(title, body, topic)
        return CommonResponse(status = HttpStatus.OK, message = "send message success")
    }
}

