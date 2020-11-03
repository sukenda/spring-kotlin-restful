package com.kenda.kotlin.restful.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("teachers")
data class Teacher(

        @Id
        val id: String,

        var fullName: String,

        var firstName: String,

        var lastName: String

)