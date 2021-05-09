package com.yolo.dokotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DokotlinApplication

fun main(args: Array<String>) {
	runApplication<DokotlinApplication>(*args)
}

