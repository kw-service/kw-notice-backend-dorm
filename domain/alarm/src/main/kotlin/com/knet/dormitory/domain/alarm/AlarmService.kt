package com.knet.dormitory.domain.alarm


interface AlarmService {
    fun sendMessage(title: String, body: String, topic: AlarmTopic)
}