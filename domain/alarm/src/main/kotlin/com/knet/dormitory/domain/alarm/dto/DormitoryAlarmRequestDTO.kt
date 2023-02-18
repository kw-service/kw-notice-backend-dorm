package com.knet.dormitory.domain.alarm.dto

import com.knet.dormitory.domain.alarm.AlarmTopic

class DormitoryAlarmRequestDTO(
    val title: String? = null,
    val body: String? = null,
    val topic: AlarmTopic? = null
)