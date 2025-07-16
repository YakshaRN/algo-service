package com.emint

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties
@ConfigurationPropertiesScan
class AlgoServiceApplication

fun main(args: Array<String>) {
    runApplication<AlgoServiceApplication>(*args)
    println("SPRING_BOOT:: Algo Service Started :)")
}
