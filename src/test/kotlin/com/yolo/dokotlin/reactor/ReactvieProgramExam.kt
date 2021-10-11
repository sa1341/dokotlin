package com.yolo.dokotlin.reactor

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.core.publisher.Flux

@ExtendWith(SpringExtension::class)
@SpringBootTest
class ReactvieProgramExam {

    @Test
    fun createAFlux_just() {
        val fruitFlux = Flux.just("Apple", "Grape", "Banana", "Strawberry")

        fruitFlux.subscribe {
            it -> println("Here's some fruit: $it")
        }
    }
}
