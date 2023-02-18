package com.knet.dormitory

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.knet.dormitory"])
class DormitoryMVCApplication

fun main(args: Array<String>) {
    runApplication<DormitoryMVCApplication>(*args)
}