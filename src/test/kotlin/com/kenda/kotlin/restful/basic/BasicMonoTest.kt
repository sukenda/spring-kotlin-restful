package com.kenda.kotlin.restful.basic

import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

internal class BasicMonoTest {

    @Test
    fun monoJust() {
      val mono = Mono.just("Kenda")

       StepVerifier.create(mono.log())
               .expectNext("Kenda")
               .expectComplete()
               .verify()
    }

}