package com.knet.dormitory.web.alarm.dto

import com.knet.dormitory.domain.alarm.AlarmTopic
import jakarta.validation.constraints.NotNull

class SendMessageRequest(
    @field:NotNull(message = "title is null")
    val title: String? = null,
    @field:NotNull(message = "body is null")
    val body: String? = null,
    @field:NotNull(message = "topic is null")
    val topic: AlarmTopic? = null
)