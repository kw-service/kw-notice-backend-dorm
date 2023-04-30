package com.knet.dormitory.domain.provider

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class NoticeIdentityProviderTest : DescribeSpec({
    val provider = NoticeIdentityProvider()
    describe("20번 이상 id를 만들어야할 때"){
        val set = (0 until 20).map { provider.generateId() }.toSet()
        it("20개 이상의 유니크한 id가 만들어져야한다."){
            set.size shouldBe 20
        }
    }
})