package com.knet.dormitory.domain.alarm.service


interface AlarmService {
    suspend fun sendMessage(title: String, body: String, topic: AlarmTopic)
    suspend fun subscribe(token: String, topic: AlarmTopic)
    suspend fun unsubscribe(token: String, topic: AlarmTopic)
}