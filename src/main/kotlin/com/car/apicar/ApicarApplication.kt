package com.car.apicar

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ApicarApplication
// gradlew bootRun
// Secao 4
fun main(args: Array<String>) {
	runApplication<ApicarApplication>(*args)
}