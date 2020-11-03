package com.kenda.kotlin.restful.service

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@DisplayName("Student Service Test")
@SpringBootTest
internal class StudentServiceTest(@Autowired val service: StudentService) {

    @Nested
    @DisplayName("Service Student Save")
    inner class SaveStudent {

        @Nested
        @DisplayName("When validation failed")
        inner class WhenValidationFailed {

            @Test
            @DisplayName("Should return error firstName missing")
            fun whenFirstNameMissing() {
                /*val student = CreateStudent(firstName = null, lastName = "Sukenda", fullName = "Kenda Sukenda")

                val firstName = service.save(student)

                StepVerifier.create(firstName.log())
                        .expectError(ConstraintViolationException::class.java)
                        .verify()*/

                // javax.validation.ConstraintViolationException: firstName: must not be blank
                /*StepVerifier.create(firstName.log())
                        .expectErrorMessage("firstName: must not be blank")
                        //.expectNext()
                        //.expectErrorMatches { throwable -> throwable == ConstraintViolationException::class && throwable.message.equals("firstName: must not be blank") }
                        .verify()*/
            }

            @Test
            @DisplayName("Should return error lastName missing")
            fun whenLastNameMissing() {
                //val student = CreateStudent(firstName = "Kenda", lastName = null, fullName = "Kenda Sukenda")
            }

            @Test
            @DisplayName("Should return error fullName missing")
            fun whenFullNameMissing() {
                //val student = CreateStudent(firstName = "Kenda", lastName = "Sukenda", fullName = null)
            }
        }

        @Nested
        @DisplayName("When validation success")
        inner class WhenValidationSuccess {

            @Test
            @DisplayName("Should return success")
            fun whenFirstNameMissing() {
                //val student = CreateStudent(firstName = "Kenda", lastName = "Sukenda", fullName = "Kenda Sukenda")
                // every { service.save(createStudent) } returns Mono.just(studentResponse)
            }
        }
    }

}