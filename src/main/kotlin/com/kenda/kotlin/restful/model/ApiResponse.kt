package com.kenda.kotlin.restful.model

data class ApiResponse<T>(

        val status: String,

        val code: Int,

        val data: T
)