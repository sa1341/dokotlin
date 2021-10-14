package com.yolo.dokotlin.reactor

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.test.StepVerifier
import java.time.Duration
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest
class ReactvieProgramExam {

    data class Player(val firstName: String, val lastName: String)


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

    @DisplayName(value = "경과 시간동안만 항목을 방출하는 take 오퍼레이션 예제")
    @Test
    fun takeDuration() {

        // given
        val nationalFlux: Flux<String> = Flux.just(
            "Yellowstone", "Yosemite", "Grand Canyon",
            "Zion", "Grand Teton"
        )
            .delayElements(Duration.ofSeconds(1))
            .take(Duration.ofMillis(3500))

        // then
        StepVerifier.create(nationalFlux)
            .expectNext("Yellowstone", "Yosemite", "Grand Canyon")
            .verifyComplete()
    }

    @DisplayName(value = "원하는 조건을 기반으로 선택적인 발행을 할 수 있는 Filter 예제")
    @Test
    fun filter() {
        // given
        val nationalParkFlux: Flux<String> = Flux.just(
            "Yellowstone", "Yosemite", "Grand Canyon",
            "Zion", "Grand Teton"
        ).filter {
            np -> !np.contains(" ")
        }

        // when
        nationalParkFlux.subscribe() {
            it -> println(it)
        }

        // then
        StepVerifier.create(nationalParkFlux)
            .expectNext("Yellowstone", "Yosemite", "Zion")
            .verifyComplete()
    }

    @DisplayName(value = "중복되지 않는 데이터 항목을 발행하는 Distinct 예제")
    @Test
    fun distinct() {
        // given
        val animalFlux: Flux<String> = Flux.just(
            "dog", "cat", "bird", "dog", "bird", "anteater"
        ).distinct()

        // when
        animalFlux.subscribe() {
                it -> println(it)
        }

        // then
        StepVerifier.create(animalFlux)
            .expectNext("dog", "cat", "bird", "anteater")
            .verifyComplete()
    }

    @DisplayName(value = "발행 항목을 다른 형태나 타입으로 매핑하는 map 오퍼레이션 예제")
    @Test
    fun map() {
        // given
        val playerFlux: Flux<Player> = Flux.just(
            "Michael Jordan", "Jean Kim", "Steve Kerr")
            .map { n ->
                val split = n.split(" ")
                Player(split[0], split[1])
            }

        // then
        StepVerifier.create(playerFlux)
            .expectNext(Player("Michael", "Jordan"))
            .expectNext(Player("Jean", "Kim"))
            .expectNext(Player("Steve", "Kerr"))
            .verifyComplete()
    }

    @DisplayName(value = "비동기적으로 수행이 가능한 flatMap 오퍼레이션 예제코드")
    @Test
    fun flatMap() {
        // given
        val playerFlux: Flux<Player> = Flux.just(
            "Michael Jordan", "Jean Kim", "Steve Kerr")
            .flatMap { n -> Mono.just(n) }
            .map { p ->
                val split = p.split(" ")
                Player(split[0], split[1])
            }
            .subscribeOn(Schedulers.parallel())

        // when
        val playerList: MutableList<Player> = mutableListOf(
            Player("Michael", "Jordan"),
            Player("Jean", "Kim"),
            Player("Steve", "Kerr")
        )

        // then
        StepVerifier.create(playerFlux)
            .expectNextMatches{ p -> playerList.contains(p)}
            .expectNextMatches{ p -> playerList.contains(p)}
            .expectNextMatches{ p -> playerList.contains(p)}
            .verifyComplete()
    }

    @DisplayName(value = "리액티브 스트림의 데이터 버퍼링 예제코드")
    @Test
    fun buffer() {
        // given
        val fruitFlux: Flux<String> = Flux.just(
            "apple", "orange", "banana", "kiwi", "strawberry")

        val bufferedFlux: Flux<List<String>> = fruitFlux.buffer(3)

        // then
        StepVerifier.create(bufferedFlux)
            .expectNext(listOf("apple", "orange", "banana"))
            .expectNext(listOf("kiwi", "strawberry"))
            .verifyComplete()
    }

    @DisplayName(value = "List 컬렉션을 병행으로 처리하는 flatMap 예제코드")
    @Test
    fun parallelBuffer() {
        // given
        val fruitFlux: Flux<String> = Flux.just(
            "apple", "orange", "banana", "kiwi", "strawberry")

        // when
        fruitFlux.buffer(3)
            .flatMap { x ->
                Flux.fromIterable(x)
                    .map { y -> y.toUpperCase() }
                    .subscribeOn(Schedulers.parallel())
                    .log()
            }.subscribe()
    }

    @DisplayName(value = "Map을 포함하는 Mono 생성 예제")
    @Test
    fun collectMap() {
        // given
        val animalFlux: Flux<String> = Flux.just(
            "elephant", "tiger", "lion", "monkey", "crocodile")

        // when
        val animalMono: Mono<Map<Char, String>> = animalFlux.collectMap{ a -> a[0]}

        animalMono.subscribe() {
            map -> println("map: ${map.toString()}")
        }

        // then
        StepVerifier.create(animalMono)
            .expectNextMatches() {
                map -> map.size == 5 &&
                    map['l'].equals("lion") &&
                    map['t'].equals("tiger") &&
                    map['c'].equals("crocodile")
            }
            .verifyComplete()
    }

    @DisplayName(value = "조건 일치를 판단하기 위한 all 로직 오퍼레이션 예제코드")
    @Test
    fun all() {
        // given
        val animalFlux: Flux<String> = Flux.just(
            "elephant", "tiger", "lion", "monkey", "crocodile")

        // when
        val hasMono: Mono<Boolean> = animalFlux.all { a -> a.contains("z")}

        // then
        StepVerifier.create(hasMono)
            .expectNext(false)
            .verifyComplete()
    }

    @DisplayName(value = "조건 일치를 판단하기 위한 any 로직 오퍼레이션 예제코드")
    @Test
    fun any() {
        // given
        val animalFlux: Flux<String> = Flux.just(
            "elephant", "tiger", "lion", "monkey", "crocodile")

        // when
        val hasZMono: Mono<Boolean> = animalFlux.any { a -> a.contains("a") }

        // then
        StepVerifier.create(hasZMono)
            .expectNext(true)
            .verifyComplete()
    }
}
