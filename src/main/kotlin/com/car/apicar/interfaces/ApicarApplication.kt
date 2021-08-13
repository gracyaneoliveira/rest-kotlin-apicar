package com.car.apicar.interfaces

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ApicarApplication
// gradlew bootRun
// Secao 5
fun main(args: Array<String>) {
	runApplication<ApicarApplication>(*args)
}
