package com.knet.dormitory.domain.alarm

import com.google.firebase.messaging.FirebaseMessaging
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk


class AlarmServiceTest: BehaviorSpec({
    val firebaseMessaging : FirebaseMessaging = mockk()
    val alarmService = AlarmService(firebaseMessaging)

    given("사용자에게 보낼 알림이 있을 때"){
        val title ="test title"
        val body = "test body"
        val topic = AlarmTopic.KW_DORM_COMMON
        `when`("알림 전송 요청이 들어오면"){
            coEvery { firebaseMessaging.send(any()) } returns "test send"
            alarmService.sendMessage(title, body, topic)

            then("알림이 전송되어야한다."){
                coVerify(exactly = 1) { firebaseMessaging.send(any()) }
            }
        }
    }
})