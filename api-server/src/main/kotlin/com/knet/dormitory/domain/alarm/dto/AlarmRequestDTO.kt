package com.knet.dormitory.domain.alarm.dto

import com.knet.dormitory.domain.alarm.service.AlarmTopic

class SubscribeAlarmRequestDTO(val token: String, val topic: AlarmTopic)
class UnsubscribeAlarmRequestDTO(val token: String, val topic: AlarmTopic)
class SendMessageRequestDTO(val title: String, val body: String, val topic: AlarmTopic)