package com.rainbow

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class RainbowApiUserApplication

fun main(args: Array<String>) {
    SpringApplication.run(RainbowApiUserApplication::class.java, *args)
}
