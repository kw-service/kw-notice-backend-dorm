package com.knet.dormitory.domain.alarm

import com.google.firebase.messaging.AndroidConfig
import com.google.firebase.messaging.AndroidNotification
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class AlarmService(
    private val firebaseMessaging: FirebaseMessaging
)  {
    private val logger = LoggerFactory.getLogger(AlarmService::class.java)

     suspend fun sendMessage(title: String, body: String, topic: AlarmTopic) {
        val notification = AndroidNotification.builder()
            .setChannelId("kw_dormitory_notice_id")
            .setTitle(title)
            .setBody(body)
            .build()

        val message = Message.builder()
            .setAndroidConfig(
                AndroidConfig.builder()
                    .setPriority(AndroidConfig.Priority.HIGH)
                    .setNotification(notification)
                    .build()
            )
            .setTopic(topic.value)
            .build()
        firebaseMessaging.sendAsync(message)
        logger.info("send message { title : ${title}, body : ${body}, topic : ${topic.value}}")
    }
}