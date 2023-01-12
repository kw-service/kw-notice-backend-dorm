package com.knet.dormitory

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DormitoryApplication

fun main(args: Array<String>) {
    runApplication<DormitoryApplication>(*args)
}