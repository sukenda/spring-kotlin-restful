package com.kenda.kotlin.restful.controller

import com.kenda.kotlin.restful.model.CreateTeacher
import com.kenda.kotlin.restful.model.StudentResponse
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
internal class TeacherControllerTest(@Autowired val client: WebTestClient) {

    @Test
    fun `Save success`() {
        val teacher = CreateTeacher(firstName = "Kenda", lastName = "Sukenda", fullName = "Kenda sukenda")
        client.post().uri("/teachers")
                .bodyValue(teacher)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody()
    }

    @Test
    fun `Save firstName Null`() {
        val teacher = CreateTeacher(firstName = null, lastName = "Sukenda", fullName = "Kenda sukenda")
        client.post().uri("/teachers")
                .bodyValue(teacher)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.data").isEqualTo("firstName: must not be blank")
    }

    @Test
    fun `Save Last Null`() {
        val teacher = CreateTeacher(firstName = "Kenda", lastName = null, fullName = "Kenda sukenda")
        client.post().uri("/teachers")
                .bodyValue(teacher)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.data").isEqualTo("lastName: must not be blank")
    }

    @Test
    fun `Save FullName Null`() {
        val teacher = CreateTeacher(firstName = "Kenda", lastName = "Sukenda", fullName = null)
        client.post().uri("/teachers")
                .bodyValue(teacher)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.data").isEqualTo("fullName: must not be blank")
    }

    @Test
    fun `Update Teacher`() {
        val teacher = CreateTeacher(firstName = "Dari budi Kenda", lastName = "Dari budi Sukenda", fullName = "Dari budi Kenda sukenda")
        client.put().uri("/teachers/{id}", "teacherId")
                .bodyValue(teacher)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                /*.jsonPath("$.data.firstName").isEqualTo("Dari budi Kenda")*/
    }

    @Test
    fun `Find Teacher By ID`() {
        client.get().uri("/teachers/{id}", "teacherId")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                /*.jsonPath("$.data.firstName").isEqualTo("Dari budi Kenda")*/
    }

    @Test
    fun `Find Teacher By ID Not Found`() {
        client.get().uri("/teachers/{id}", "tidak-ditemukan")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.data").isEqualTo("Data tidak ditemukan")
    }

    @Test
    fun `Find Teacher ALL`() {
        client.get().uri(({ uriBuilder ->
            uriBuilder.path("/teachers")
                    .queryParam("page", "0")
                    .queryParam("size", "10")
                    .build()
        }))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBodyList(StudentResponse::class.java)
    }
}