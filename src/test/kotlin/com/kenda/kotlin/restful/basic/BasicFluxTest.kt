package com.kenda.kotlin.restful.basic

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import reactor.util.function.Tuples
import java.time.Duration
import java.util.concurrent.TimeUnit
import java.util.function.BiFunction

/**
 * https://nirajsonawane.github.io/2020/01/01/Part-2-Process-and-Transform-Flux-Mono/
 */
internal class BasicFluxTest {

    @Test
    fun fluxExpectNext() {
        val flux = Flux.just(1, 2, 3)

        StepVerifier.create(flux.log())
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .verifyComplete()
    }

    @Test
    fun fluxExpectNexCount() {
        val flux = Flux.just(1, 2, 3)

        StepVerifier.create(flux.log())
                .expectNextCount(3)
                .verifyComplete()
    }

    @Test
    fun fluxWithError() {
        val flux = Flux.just(1).concatWith(Flux.error(RuntimeException("RuntimeException")))

        StepVerifier.create(flux)
                .expectNextCount(1)
                .verifyError(RuntimeException::class.java)
    }

    /**
     * Filter value by predicate
     */
    @Test
    fun fluxFilter() {
        val fluxNumbers = Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val fluxFilter1 = fluxNumbers.filter { it % 2 == 0 }
        val fluxFilter2 = fluxNumbers.filter { it % 2 == 1 }

        StepVerifier.create(fluxFilter1.log())
                .expectNext(2, 4, 6, 8, 10)
                .verifyComplete()

        StepVerifier.create(fluxFilter2.log())
                .expectNext(1, 3, 5, 7, 9)
                .verifyComplete()

    }

    /**
     * distinct use for filter duplicate value
     */
    @Test
    fun fluxDistinct() {
        val fluxNumbers = Flux.just(1, 2, 3, 4, 5, 1, 2, 3, 4, 5)
        val distinct = fluxNumbers.distinct()

        StepVerifier
                .create(distinct.log())
                .expectNext(1, 2, 3, 4, 5)
                .verifyComplete()
    }

    /**
     * Consume value from flux until predicate return TRUE for the value
     */
    @Test
    fun fluxTakeWhile() {
        val fluxNumbers = Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val lessThen5 = fluxNumbers.takeWhile { it <= 5 }

        StepVerifier.create(lessThen5.log())
                .expectNext(1, 2, 3, 4, 5)
                .verifyComplete()
    }

    /**
     * Skip value until predicate return TRUE for the value
     */
    @Test
    fun fluxSkipWhile() {
        val fluxNumbers = Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val greaterThen5 = fluxNumbers.skipWhile { it <= 5 }

        StepVerifier.create(greaterThen5.log())
                .expectNext(6, 7, 8, 9, 10)
                .verifyComplete()
    }

    /**
     * Map operation is use for transforming element from one Type to another
     */
    @Test
    fun fluxMap() {
        val fluxStrings = Flux.just("Kenda", "Sukenda", "Kenda Sukenda")
        val fluxNumbers = fluxStrings.map { it.length }

        StepVerifier.create(fluxNumbers.log())
                .expectNext(5, 7, 13)
                .verifyComplete()
    }

    @Test
    fun fluxFlatMap() {
        val flux = Flux.just(1, 2, 3)
        val fluxNumbers = flux.flatMap { range(it) }

        StepVerifier.create(fluxNumbers.log())
                .expectNextCount(30)
                .verifyComplete()
    }

    private fun range(value: Int): Flux<Int> {
        return Flux.range(value, 10)
    }

    @Test
    fun fluxIndex() {
        val flux = Flux.just("Kenda", "Sukenda", "Kenda Sukenda")
        val fluxIndex = flux.index()

        StepVerifier.create(fluxIndex.log())
                .expectNext(Tuples.of(0L, "Kenda"))
                .expectNext(Tuples.of(1L, "Sukenda"))
                .expectNext(Tuples.of(2L, "Kenda Sukenda"))
                .verifyComplete()
    }

    @Test
    fun fluxFlatMapMany() {
        val mono = Mono.just(listOf(1, 2, 3, 4, 5))
        val fluxFlatMap = mono.flatMapMany { Flux.fromIterable(it) }

        StepVerifier.create(fluxFlatMap.log())
                .expectNext(1, 2, 3, 4, 5)
                .verifyComplete()

    }

    @Test
    fun fluxStartWith() {
        val fluxNumbers = Flux.just(1, 2, 3)
        val fluxStart = fluxNumbers.startWith(0)

        StepVerifier.create(fluxStart.log())
                .expectNext(0, 1, 2, 3)
                .verifyComplete()
    }

    @Test
    fun fluxConcatWith() {
        val fluxStart = Flux.just(1, 2, 3)
        val fluxEnd = fluxStart.concatWith(Flux.just(4, 5))

        StepVerifier.create(fluxEnd.log())
                .expectNext(1, 2, 3, 4, 5)
                .verifyComplete()
    }

    @Test
    fun fluxMargeWith() {
        val fluxFirst = Flux.just(1, 2, 3, 4, 5).delayElements(Duration.ofSeconds(1))
        val fluxSecond = Flux.just(10, 20, 30, 40, 50).delayElements(Duration.ofSeconds(1))

        fluxFirst.mergeWith(fluxSecond).subscribe { println(it) }

        // This will print numbers received from firsFlux and secondFlux in random order
        TimeUnit.SECONDS.sleep(11)
    }

    @Test
    fun fluxCollectList() {
        val fluxNumbers = Flux.just(1, 2, 3).collectList()

        StepVerifier.create(fluxNumbers.log())
                .expectNext(listOf(1, 2, 3))
                .verifyComplete()
    }

    @Test
    fun fluxCollectSortedList() {
        val fluxNumbers = Flux.just(3, 2, 1).collectSortedList()

        StepVerifier.create(fluxNumbers.log())
                .expectNext(listOf(1, 2, 3))
                .verifyComplete()
    }

    @Test
    fun fluxZip() {
        val fluxFirst = Flux.just(1, 2, 3)
        val fluxSecond = Flux.just(10, 20, 30)

        val add = BiFunction<Int, Int, Int> { t1, t2 -> t1 + t2 }

        val fluxZip = Flux.zip(fluxFirst, fluxSecond, add)

        StepVerifier.create(fluxZip.log())
                .expectNext(11, 22, 33)
                .verifyComplete()

    }

    @Test
    fun fluxBuffer() {
        val flux = Flux.just(1, 2, 3, 4, 5)
        val fluxBuffer = flux.buffer(2)

        StepVerifier.create(fluxBuffer.log())
                .expectNext(listOf(1, 2))
                .expectNext(listOf(3, 4))
                .expectNext(listOf(5))
                .verifyComplete()
    }

}