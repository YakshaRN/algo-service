package com.emint

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class AlgoServiceApplication

fun main(args: Array<String>) {
    runApplication<AlgoServiceApplication>(*args)
    println("SPRING_BOOT:: Algo Service Started :)")
}
