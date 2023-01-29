package com.knet.dormitory.web.alarm

import com.knet.dormitory.domain.alarm.AlarmService
import com.knet.dormitory.domain.alarm.AlarmTopic
import com.knet.dormitory.web.alarm.dto.SendMessageRequest
import com.knet.dormitory.web.shared.CommonResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/alarm")
class AlarmController(
    private val alarmService: AlarmService
) {
    @PostMapping("/test")
    fun test() {
        alarmService.sendMessage("test title", "test body", AlarmTopic.KW_DORM_COMMON)
    }

    @PostMapping("/send")
    fun sendMessage(@RequestBody dto: SendMessageRequest): CommonResponse {
        if (isValid(dto)) throw IllegalStateException("입력값에 null이 있으면 안됩니다.")
        alarmService.sendMessage(dto.title!!, dto.body!!, dto.topic!!)
        return CommonResponse(status = HttpStatus.OK, message = "알림이 성공적으로 보내졌습니다.")
    }

    fun isValid(dto: SendMessageRequest): Boolean = dto.body == null || dto.title == null || dto.topic == null
}

