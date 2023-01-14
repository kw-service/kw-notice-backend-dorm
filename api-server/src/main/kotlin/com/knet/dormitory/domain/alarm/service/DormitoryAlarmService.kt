package com.knet.dormitory.domain.alarm.service

import com.google.firebase.messaging.AndroidConfig
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class DormitoryAlarmService(private val firebaseMessaging: FirebaseMessaging) : AlarmService {

    private val logger = Logger.getLogger("dormitory alarm service")
    override suspend fun sendMessage(title: String, body: String, topic: AlarmTopic) {
        val message = Message.builder()
            .setTopic(topic.name)
            .setAndroidConfig(
                AndroidConfig.builder()
                    .setPriority(AndroidConfig.Priority.HIGH)
                    .build()
            )
            .build()
        firebaseMessaging.sendAsync(message)
        logger.info("send message $message")
    }


    override suspend fun subscribe(token: String, topic: AlarmTopic) {
        firebaseMessaging.subscribeToTopicAsync(arrayListOf(token), topic.name)
        logger.info("subscribe $token , type : ${topic.name}")
    }

    override suspend fun unsubscribe(token: String, topic: AlarmTopic) {
        firebaseMessaging.unsubscribeFromTopicAsync(arrayListOf(token), topic.name)
        logger.info("unsubscribe $token , type : ${topic.name}")
    }
}