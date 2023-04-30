package com.knet.dormitory.domain.provider

import org.springframework.stereotype.Service
import java.util.UUID

@Service
class NoticeIdentityProvider {
    fun generateId() : String = UUID.randomUUID().toString()
}