package com.knet.dormitory.presentation

import com.knet.dormitory.domain.alarm.dto.SendMessageRequestDTO
import com.knet.dormitory.domain.alarm.dto.SubscribeAlarmRequestDTO
import com.knet.dormitory.domain.alarm.dto.UnsubscribeAlarmRequestDTO
import com.knet.dormitory.domain.alarm.service.AlarmService
import com.knet.dormitory.shard.dto.CommonResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class AlarmHandler(
    private val alarmService: AlarmService
) {
    suspend fun subscribe(request: ServerRequest): ServerResponse {
        val dto = request.awaitBody(SubscribeAlarmRequestDTO::class)
        alarmService.subscribe(dto.token, dto.topic)

        val res = CommonResponse(
            status = HttpStatus.OK,
            message = "요청이 성공적으로 보내졌습니다."
        )
        return ok().bodyValueAndAwait(res)
    }

    suspend fun unsubscribe(request: ServerRequest): ServerResponse {
        val dto = request.awaitBody(UnsubscribeAlarmRequestDTO::class)
        alarmService.unsubscribe(dto.token, dto.topic)

        val res = CommonResponse(
            status = HttpStatus.OK,
            message = "요청이 성공적으로 보내졌습니다."
        )
        return ok().bodyValueAndAwait(res)
    }

    suspend fun send(request: ServerRequest): ServerResponse {
        val dto = request.awaitBody(SendMessageRequestDTO::class)
        alarmService.sendMessage(dto.title, dto.body, dto.topic)

        val res = CommonResponse(
            status = HttpStatus.OK,
            message = "메세지가 성공적으로 보내졌습니다."
        )
        return ok().bodyValueAndAwait(res)
    }
}

