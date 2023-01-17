package com.knet.dormitory.web.alarm.dto

import com.knet.dormitory.domain.alarm.AlarmTopic

class SendMessageRequest(
    val title: String? = null,
    val body: String? = null,
    val topic: AlarmTopic? = null
)