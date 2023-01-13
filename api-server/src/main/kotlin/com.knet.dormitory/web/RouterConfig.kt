package com.knet.dormitory.web

import com.knet.dormitory.presentation.NoticeHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class RouterConfig(
    private val noticeHandler: NoticeHandler
) {
    @Bean
    fun router() = coRouter {
        "/notice".nest {
            GET("") { noticeHandler.getNoticeList() }
        }
    }
}