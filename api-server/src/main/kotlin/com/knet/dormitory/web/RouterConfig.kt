package com.knet.dormitory.web

import com.knet.dormitory.presentation.AlarmHandler
import com.knet.dormitory.presentation.NoticeHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class RouterConfig(
    private val noticeHandler: NoticeHandler,
    private val alarmHandler: AlarmHandler
) {
    @Bean
    fun router() = coRouter {
        "/notice".nest {
            GET("") { noticeHandler.getNoticeList() }
        }
        "alarm".nest {
            POST("/subscribe", alarmHandler::subscribe)
            POST("/unsubscribe", alarmHandler::unsubscribe)
            POST("/send", alarmHandler::send)
        }
    }
}