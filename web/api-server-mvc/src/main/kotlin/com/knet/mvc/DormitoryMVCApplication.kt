package com.knet.mvc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.knet"])
class DormitoryMVCApplication

fun main(args: Array<String>) {
    runApplication<DormitoryMVCApplication>(*args)
}