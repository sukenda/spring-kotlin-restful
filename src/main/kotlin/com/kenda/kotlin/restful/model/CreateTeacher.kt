package com.kenda.kotlin.restful.model

import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class CreateTeacher (

        val id: String? = UUID.randomUUID().toString(),

        @field: NotBlank
        @field: Size(max = 25)
        var firstName: String?,

        @field: NotBlank
        @field: Size(max = 25)
        var lastName: String?,

        @field: NotBlank
        @field: Size(max = 50)
        var fullName: String?
)