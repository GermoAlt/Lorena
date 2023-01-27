package com.buffer.lorena

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableMongoRepositories
class LorenaApplication

fun main(args: Array<String>) {
    runApplication<LorenaApplication>(*args)
}