package com.yolo.dokotlin

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.retry.annotation.EnableRetry


@EnableJpaAuditing
@EnableRetry
@EnableCaching
@SpringBootApplication
class DokotlinApplication: CommandLineRunner {

	override fun run(vararg args: String?) {
		println("애플리케이션 실행한다!")
	}
}

fun main(args: Array<String>) {
	runApplication<DokotlinApplication>(*args)
}

