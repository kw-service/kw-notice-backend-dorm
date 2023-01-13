package com.knet.dormitory

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DormitoryAPIApplication

fun main(args: Array<String>) {
    runApplication<DormitoryAPIApplication>(*args)
}