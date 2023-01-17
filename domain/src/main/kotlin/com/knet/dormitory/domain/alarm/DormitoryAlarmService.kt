package com.knet.dormitory.domain.alarm

import com.knet.dormitory.domain.alarm.dto.DormitoryAlarmRequestDTO
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class DormitoryAlarmService(
    private val webClient: WebClient
) : AlarmService {
    private val logger = LoggerFactory.getLogger(DormitoryAlarmService::class.java)

    private val BASE_URL = "http://localhost:8883"

    override fun sendMessage(title: String, body: String, topic: AlarmTopic) {
        logger.info("start send message $title  : $body : $topic")
        webClient.post()
            .uri("${BASE_URL}/alarm/send")
            .bodyValue(DormitoryAlarmRequestDTO(title, body, topic))
            .retrieve()
            .toEntity(String::class.java)
            .onErrorContinue { _, _ -> logger.warn("Error send message {$title : $body}") }
            .block()
    }
}