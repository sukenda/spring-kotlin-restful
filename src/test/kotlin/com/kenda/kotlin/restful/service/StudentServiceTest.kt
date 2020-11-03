package com.kenda.kotlin.restful.service

import com.kenda.kotlin.restful.model.CreateStudent
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.test.StepVerifier
import javax.validation.ConstraintViolation
import javax.validation.Validation.buildDefaultValidatorFactory
import javax.validation.Validator
import javax.validation.ValidatorFactory


@DisplayName("Student Service Test")
@SpringBootTest
@Tag("integration-test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class StudentServiceTest(@Autowired val service: StudentService) {

    private var validatorFactory: ValidatorFactory? = null

    private var validator: Validator? = null

    @BeforeAll
    fun open() {
        validatorFactory = buildDefaultValidatorFactory()
        validator = validatorFactory!!.validator
    }

    @AfterAll
    fun close() {
        validatorFactory!!.close()
    }

    @Nested
    @DisplayName("Service Student Save")
    inner class SaveStudent {

        @Nested
        @DisplayName("When validation failed")
        inner class WhenValidationFailed {

            @Test
            @DisplayName("Should return error firstName missing")
            fun whenFirstNameMissing() {
                val student = CreateStudent(firstName = null, lastName = "Sukenda", fullName = "Kenda Sukenda")

                val violations: Set<ConstraintViolation<CreateStudent>> = validator!!.validate(student)

                assertEquals(violations.size, 1)

                val violation: ConstraintViolation<CreateStudent> = violations.iterator().next()
                assertEquals("must not be blank", violation.message)
                assertEquals("firstName", violation.propertyPath.toString())
                assertEquals(null, violation.invalidValue)

            }

            @Test
            @DisplayName("Should return error lastName missing")
            fun whenLastNameMissing() {
                val student = CreateStudent(firstName = "Kenda", lastName = null, fullName = "Kenda Sukenda")

                val violations: Set<ConstraintViolation<CreateStudent>> = validator!!.validate(student)

                assertEquals(violations.size, 1)

                val violation: ConstraintViolation<CreateStudent> = violations.iterator().next()
                assertEquals("must not be blank", violation.message)
                assertEquals("lastName", violation.propertyPath.toString())
                assertEquals(null, violation.invalidValue)

            }

            @Test
            @DisplayName("Should return error fullName missing")
            fun whenFullNameMissing() {
                val student = CreateStudent(firstName = "Kenda", lastName = "Sukenda", fullName = null)

                val violations: Set<ConstraintViolation<CreateStudent>> = validator!!.validate(student)

                assertEquals(violations.size, 1)

                val violation: ConstraintViolation<CreateStudent> = violations.iterator().next()
                assertEquals("must not be blank", violation.message)
                assertEquals("fullName", violation.propertyPath.toString())
                assertEquals(null, violation.invalidValue)

            }
        }

        @Nested
        @DisplayName("When validation success")
        inner class WhenValidationSuccess {

            @Test
            @DisplayName("Should return success")
            fun whenFirstNameMissing() {
                val student = CreateStudent(firstName = "Kenda", lastName = "Sukenda", fullName = "Kenda Sukenda")

                val violations: Set<ConstraintViolation<CreateStudent>> = validator!!.validate(student)
                assertTrue(violations.isEmpty());

                val saved = service.save(student)

                // if db running
                StepVerifier.create(saved.log())
                        .thenConsumeWhile { it.firstName == "Kenda" }
                        .verifyComplete()

                // If db not running
                /*StepVerifier.create(saved.log())
                        .expectError(DataAccessResourceFailureException::class.java)
                        .verify()*/
            }
        }
    }

    @Nested
    @DisplayName("Service Student Update")
    inner class UpdateStudent {

        @Nested
        @DisplayName("When validation failed")
        inner class WhenValidationFailed() {

        }

        @Nested
        @DisplayName("When validation success")
        inner class WhenValidationSuccess() {

        }

        @Nested
        @DisplayName("When student not found")
        inner class WhenStudentNotFound() {

        }

        @Nested
        @DisplayName("When student found")
        inner class WhenStudentFound() {

        }

    }

    @Nested
    @DisplayName("Service Student FindById")
    inner class FindByIdStudent() {

    }

    @Nested
    @DisplayName("Service Student Find")
    inner class FindStudent() {

    }

}