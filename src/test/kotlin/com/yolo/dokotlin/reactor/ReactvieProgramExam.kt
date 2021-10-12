package com.yolo.dokotlin.reactor

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.time.Duration

@ExtendWith(SpringExtension::class)
@SpringBootTest
class ReactvieProgramExam {

    @DisplayName(value = "Collection 객체의 과일이름들을 방출하는 Flux 객체 생성 예제")
    @Test
    fun createAFlux_just() {
        val fruits = arrayListOf<String>()
        fruits.add("Apple")
        fruits.add("Grape")
        fruits.add("Banana")
        fruits.add("Strawberry")
        val fruitFlux = Flux.fromIterable(fruits)

        fruitFlux.subscribe { it ->
            println("Here's some fruit: $it")
        }

        StepVerifier.create(fruitFlux)
            .expectNext("Apple")
            .expectNext("Grape")
            .expectNext("Banana")
            .expectNext("Strawberry")
            .verifyComplete()
    }

    @DisplayName(value = "일점 범위의 값을 포함하는 카운터 Flux 생성 예제")
    @Test
    fun createAFlux_range() {

        // given
        val intervalFlux: Flux<Int> = Flux.range(1, 5)

        // when
        intervalFlux.subscribe() { it ->
            println("num: $it")
        }

        // then
        StepVerifier.create(intervalFlux)
            .expectNext(1)
            .expectNext(2)
            .expectNext(3)
            .expectNext(4)
            .expectNext(5)
            .verifyComplete()
    }

    @DisplayName(value = "일점 범위의 값을 시간 간격으로 방출하는 Flux 생성 예제")
    @Test
    fun createAFlux_interval() {
        // given
        val intervalFlux: Flux<Long> = Flux.interval(Duration.ofSeconds(1)).take(5)

        // when
        intervalFlux.subscribe() { it ->
            println("num: $it")
        }

        // then
        StepVerifier.create(intervalFlux)
            .expectNext(0L)
            .expectNext(1L)
            .expectNext(2L)
            .expectNext(3L)
            .expectNext(4L)
            .verifyComplete()
    }

    @DisplayName(value = "리액티브 타입 결합하는 예제")
    @Test
    fun mergeFluxes() {
        // given
        val characterFlux = Flux
            .just("Garfield", "Kojak", "Barbossa")
            .delayElements(Duration.ofMillis(500))

        val foodFlux = Flux
            .just("Lasagna", "Lollipops", "Apples")
            .delaySubscription(Duration.ofMillis(250)) // 구독 지연
            .delayElements(Duration.ofMillis(500)) // 이벤트 발행 지연

        val mergeFlux = characterFlux.mergeWith(foodFlux)

        // then
        StepVerifier.create(mergeFlux)
            .expectNext("Garfield")
            .expectNext("Lasagna")
            .expectNext("Kojak")
            .expectNext("Lollipops")
            .expectNext("Barbossa")
            .expectNext("Apples")
            .verifyComplete()
    }

    @DisplayName(value = "소스 flux 스트림을 완벽히 번갈아서 구독하는 zip 오퍼레이션 예제")
    @Test
    fun zioFluxes() {
        // given
        val characterFlux: Flux<String> = Flux
            .just("Garfield", "Kojak", "Barbossa")
            .delayElements(Duration.ofMillis(500))


        val foodFlux: Flux<String> = Flux
            .just("Lasagna", "Lollipops", "Apples")

        val zippedFlux: Flux<String> = Flux.zip(characterFlux, foodFlux) { c, f ->
            "$c eats $f"
        }

        // then
        StepVerifier.create(zippedFlux)
            .expectNext("Garfield eats Lasagna")
            .expectNext("Kojak eats Lollipops")
            .expectNext("Barbossa eats Apples")
            .verifyComplete()
    }

    @DisplayName(value = "먼저 값을 방출하는 소스 Flux의 값을 발행하는 새로운 Flux 생성 예제")
    @Test
    fun firstFlux() {

        // given
        val slowFlux: Flux<String> = Flux.just("tortoise", "snail", "sloth")
            .delaySubscription(Duration.ofMillis(100))

        val fastFlux: Flux<String> = Flux.just("hare", "cheetah", "squirrel")

        val firstFlux: Flux<String> = Flux.first(slowFlux, fastFlux)

        // then
        StepVerifier.create(firstFlux)
            .expectNext("hare")
            .expectNext("cheetah")
            .expectNext("squirrel")
            .verifyComplete()
    }

    @DisplayName(value = "소스 Flux에서 특정 항목을 건너띄는 skip 오퍼레이션 예제")
    @Test
    fun skipAFew() {
        // given
        val skipFlux: Flux<String> = Flux.just(
            "one", "two", "skip a few", "ninety nine", "one hundred"
        )
            .skip(3)

        // when
        skipFlux.subscribe() { it ->
            println(it)
        }

        // then
        StepVerifier.create(skipFlux)
            .expectNext("ninety nine", "one hundred")
            .verifyComplete()
    }

    @DisplayName(value = "소스 Flux에서 일정 시간이 경과할 때까지 처음 여러 항목을 건너띄는 skip 오퍼레이션 예제")
    @Test
    fun skipAFewSeconds() {
        // given
        val skipFlux: Flux<String> = Flux.just(
            "one", "two", "skip a few", "ninety nine", "one hundred"
        )
            .delayElements(Duration.ofSeconds(1))
            .skip(Duration.ofSeconds(4))

        // when
        skipFlux.subscribe() { it ->
            println(it)
        }

        // then
        StepVerifier.create(skipFlux)
            .expectNext("ninety nine", "one hundred")
            .verifyComplete()
    }

    @DisplayName(value = "지정된 수의 항목만을 방출하는 take 오퍼레이션 예제")
    @Test
    fun take() {

        // given
        val nationalFlux: Flux<String> = Flux.just(
            "Yellowstone", "Yosemite", "Grand Canyon",
            "Zion", "Grand Teton"
        ).take(3)

        // then
        StepVerifier.create(nationalFlux)
            .expectNext("Yellowstone", "Yosemite", "Grand Canyon")
            .verifyComplete()
    }
}
