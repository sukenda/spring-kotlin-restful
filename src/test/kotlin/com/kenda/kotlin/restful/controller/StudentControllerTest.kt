package com.kenda.kotlin.restful.controller

import com.kenda.kotlin.restful.model.CreateStudent
import com.kenda.kotlin.restful.model.StudentResponse
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
internal class StudentControllerTest(@Autowired val client: WebTestClient) {

    @Test
    fun `Save success`() {
        val student = CreateStudent(firstName = "Kenda", lastName = "Sukenda", fullName = "Kenda sukenda")
        client.post().uri("/students")
                .bodyValue(student)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody()
    }

    @Test
    fun `Save firstName Null`() {
        val student = CreateStudent(firstName = null, lastName = "Sukenda", fullName = "Kenda sukenda")
        client.post().uri("/students")
                .bodyValue(student)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.data").isEqualTo("firstName: must not be blank")
    }

    @Test
    fun `Save Last Null`() {
        val student = CreateStudent(firstName = "Kenda", lastName = null, fullName = "Kenda sukenda")
        client.post().uri("/students")
                .bodyValue(student)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.data").isEqualTo("lastName: must not be blank")
    }

    @Test
    fun `Save FullName Null`() {
        val student = CreateStudent(firstName = "Kenda", lastName = "Sukenda", fullName = null)
        client.post().uri("/students")
                .bodyValue(student)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.data").isEqualTo("fullName: must not be blank")
    }

    @Test
    fun `Update student`() {
        val student = CreateStudent(firstName = "Dari budi Kenda", lastName = "Dari budi Sukenda", fullName = "Dari budi Kenda sukenda")
        client.put().uri("/students/{id}", "studentId")
                .bodyValue(student)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody()
        /*.jsonPath("$.data.firstName").isEqualTo("Dari budi Kenda")*/
    }

    @Test
    fun `Find Student By ID`() {
        client.get().uri("/students/{id}", "studentId")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody()
        /*.jsonPath("$.data.firstName").isEqualTo("Dari budi Kenda")*/
    }

    @Test
    fun `Find Student By ID Not Found`() {
        client.get().uri("/students/{id}", "tidak-ditemukan")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.data").isEqualTo("Data tidak ditemukan")
    }

    @Test
    fun `Find Student ALL`() {
        client.get().uri(({ uriBuilder ->
            uriBuilder.path("/students")
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