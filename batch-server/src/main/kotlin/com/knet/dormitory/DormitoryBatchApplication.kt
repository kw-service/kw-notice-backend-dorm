package com.knet.dormitory

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DormitoryBatchApplication

fun main(args: Array<String>) {
    runApplication<DormitoryBatchApplication>(*args)
}