package com.knet.dormitory

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.knet"])
class DormitoryBatchApplication

fun main(args: Array<String>) {
    runApplication<DormitoryBatchApplication>(*args)
}