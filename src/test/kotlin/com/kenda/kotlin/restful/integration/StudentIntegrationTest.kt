package com.kenda.kotlin.restful.integration

import com.kenda.kotlin.restful.model.CreateStudent
import com.kenda.kotlin.restful.model.StudentResponse
import com.kenda.kotlin.restful.service.StudentService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@Tag("integration-test")
@DisplayName("Integration Test Student")
@AutoConfigureWebTestClient
@SpringBootTest
class StudentIntegrationTest(@Autowired val client: WebTestClient,@Autowired val service: StudentService) {

    @Nested
    @DisplayName("Service Student Test")
    inner class ServiceTest {

        @Nested
        @DisplayName("Save")
        inner class Save {
            private var createStudent = CreateStudent(firstName = null, lastName = null, fullName = null)
            private var studentResponse = StudentResponse(id = null, firstName = null, lastName = null, fullName = null)
            private val maxLengthName = 25
            private val maxLengthFullName = 50

            @Nested
            @DisplayName("When validation failed")
            inner class WhenValidationFailed {

                @Test
                @DisplayName("Should return error firstName missing")
                fun whenFirstNameMissing() {
                   // every { service.save(createStudent) } returns Mono.just(studentResponse)
                }

                @Test
                @DisplayName("Should return error lastName missing")
                fun whenLastNameMissing() {

                }

                @Test
                @DisplayName("Should return error fullName missing")
                fun whenFullNameMissing() {

                }
            }

            @Nested
            @DisplayName("When validation max length failed")
            inner class WhenMaxLengthFailed {

                @Test
                @DisplayName("Should return error firstName greater than max length")
                fun whenFirstNameGreaterThanMaxLength() {
                  // createStudent.firstName = TestStringBuilder.createStringWithLength(maxLengthName + 1)

                }

                @Test
                @DisplayName("Should return error lastName greater than max length")
                fun whenLastNameGreaterThanMaxLength() {
                  // createStudent.lastName = TestStringBuilder.createStringWithLength(maxLengthName + 1)

                }

                @Test
                @DisplayName("Should return error fullName greater than max length")
                fun whenFullNameGreaterThanMaxLength() {
                   // createStudent.fullName = TestStringBuilder.createStringWithLength(maxLengthFullName + 1)

                }
            }

            @Nested
            @DisplayName("When validation success")
            inner class WhenValidationSuccess() {

            }
        }

        @Nested
        @DisplayName("Update")
        inner class Update {

        }

        @Nested
        @DisplayName("FindById")
        inner class FindById {

        }

        @Nested
        @DisplayName("Find")
        inner class Find {

        }

    }

    @Nested
    @DisplayName("Controller Student Test")
    inner class ControllerTest {

        @Nested
        @DisplayName("Save")
        inner class Save {

            private val maxLengthName = 25
            private val maxLengthFullName = 50

            @Nested
            @DisplayName("When validation failed")
            inner class WhenValidationFailed {

                private val errorCodeBlank = "NotBlank"
                private val errorCodeSize = "Size"
                private val fieldNameFirstName = "firstName"

                @Nested
                @DisplayName("When the firstName lastName and fullName are missing")
                inner class WhenInputMissing {
                    private val student = CreateStudent(firstName = null, lastName = null, fullName = null)

                    @Test
                    @DisplayName("Should return the HTTP status code bad request")
                    fun shouldReturnHttpStatusCodeBadRequest() {
                        /*client.post().uri("/students")
                                .bodyValue(student)
                                .accept(MediaType.APPLICATION_JSON)
                                .exchange()
                                .expectStatus().isOk
                                .expectBody()
                                .jsonPath("$.status").isEqualTo("BAD REQUEST")
                                .jsonPath("$.code").isEqualTo("400")*/
                    }

                    @Test
                    @DisplayName("Should return validation errors")
                    fun shouldReturnValidationErrors() {
                        /*client.post().uri("/students")
                                .bodyValue(createStudent)
                                .exchange()
                                .expectStatus().isOk
                                .expectBody()
                                .jsonPath("$.data").isEqualTo("BAD REQUEST")*/
                    }

                    @Test
                    @DisplayName("Should return one validation error")
                    fun shouldReturnOneValidationError() {
                        /*requestBuilder.create("/students", createStudent)
                                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors", Matchers.hasSize<Any>(1)))*/
                    }

                    @Test
                    @DisplayName("Should return a validation error about missing firstName")
                    fun shouldReturnValidationErrorAboutMissingFirstName() {
                        /*requestBuilder.create("/students", createStudent)
                                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].field", Matchers.equalTo(fieldNameFirstName)))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].errorCode", Matchers.equalTo(errorCodeBlank)))*/
                    }
                }

            }

        }

    }

}