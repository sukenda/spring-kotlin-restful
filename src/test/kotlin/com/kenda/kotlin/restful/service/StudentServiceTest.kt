package com.kenda.kotlin.restful.service

import com.kenda.kotlin.restful.exception.DataNotFoundException
import com.kenda.kotlin.restful.model.CreateStudent
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import reactor.test.StepVerifier
import javax.validation.ConstraintViolation
import javax.validation.Validation.buildDefaultValidatorFactory
import javax.validation.Validator
import javax.validation.ValidatorFactory


@SpringBootTest
@DisplayName("Student Service Test")
@ActiveProfiles("integration-test")
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
                assertTrue(violations.isEmpty())

                val saved = service.save(student)

                StepVerifier.create(saved.log())
                        .thenConsumeWhile { it.firstName == "Kenda" }
                        .verifyComplete()
            }
        }
    }

    @Nested
    @DisplayName("Service Student Update")
    inner class UpdateStudent {

        @Nested
        @DisplayName("When student not found")
        inner class WhenStudentNotFound {

            @Test
            @DisplayName("Should return error validate")
            fun whenStudentNotFoundAndValidateError() {
                val student = CreateStudent(firstName = null, lastName = "Sukenda", fullName = "Kenda Sukenda")

                val violations: Set<ConstraintViolation<CreateStudent>> = validator!!.validate(student)

                assertEquals(violations.size, 1)
            }

            @Test
            @DisplayName("Should return error not found")
            fun whenStudentNotFound() {
                val student = CreateStudent(firstName = "Kenda", lastName = "Sukenda", fullName = "Kenda Sukenda")

                val notFound = service.update("Tidak ada", student)

                StepVerifier.create(notFound.log())
                        .expectError(DataNotFoundException::class.java)
                        .verify()
            }

        }

        @Nested
        @DisplayName("When student found")
        inner class WhenStudentFound {

            @Test
            @DisplayName("Should return error validate")
            fun whenFoundButErrorValidate() {
                val student = CreateStudent(firstName = null, lastName = "Sukenda", fullName = "Kenda Sukenda")

                val violations: Set<ConstraintViolation<CreateStudent>> = validator!!.validate(student)

                assertEquals(violations.size, 1)
            }

            @Test
            @DisplayName("Should return success")
            fun whenFoundAndSuccessUpdate() {
                val student = CreateStudent(firstName = "Kenda update", lastName = "Sukenda", fullName = "Kenda Sukenda")

                val notFound = service.update("606a4ca3-1999-4478-8132-6f52864cc136", student)

                StepVerifier.create(notFound.log())
                        .thenConsumeWhile { t -> t.firstName === "Kenda update" }
                        .verifyComplete()
            }

        }

    }

    @Nested
    @DisplayName("Service Student FindById")
    inner class FindByIdStudent {

        @Test
        @DisplayName("Should return not found")
        fun findByIdFound() {
            val notFound = service.findById("Tidak ada")

            StepVerifier.create(notFound.log())
                    .expectError(DataNotFoundException::class.java)
                    .verify()
        }

        @Test
        @DisplayName("Should return found")
        fun findByIdNotFound() {
            val found = service.findById("606a4ca3-1999-4478-8132-6f52864cc136")

            StepVerifier.create(found.log())
                    .thenConsumeWhile { t -> t.id != null }
                    .verifyComplete()
        }
    }

    @Nested
    @DisplayName("Service Student Find")
    inner class FindStudent {

        @Nested
        @DisplayName("When find params not valid")
        inner class WhenNotValidParams {

        }

        @Nested
        @DisplayName("When find params valid")
        inner class WhenValidParams {

        }

    }

}