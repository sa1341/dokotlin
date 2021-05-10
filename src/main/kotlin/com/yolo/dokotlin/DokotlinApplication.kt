package com.yolo.dokotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class DokotlinApplication

fun main(args: Array<String>) {
	runApplication<DokotlinApplication>(*args)
}

