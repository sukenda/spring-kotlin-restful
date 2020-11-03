package com.kenda.kotlin.restful.service

import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory

@DisplayName("Teacher Service Test")
@SpringBootTest
@Tag("integration-test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TeacherServiceTest(@Autowired val service: TeacherService) {

    private var validatorFactory: ValidatorFactory? = null

    private var validator: Validator? = null

    @BeforeAll
    fun open() {
        validatorFactory = Validation.buildDefaultValidatorFactory()
        validator = validatorFactory!!.validator
    }

    @AfterAll
    fun close() {
        validatorFactory!!.close()
    }

    @Test
    fun save() {
    }

    @Test
    fun update() {
    }

    @Test
    fun findById() {
    }

    @Test
    fun find() {
    }
}