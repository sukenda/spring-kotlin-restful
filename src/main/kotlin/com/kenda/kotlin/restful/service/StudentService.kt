package com.kenda.kotlin.restful.service

import com.kenda.kotlin.restful.model.CreateStudent
import com.kenda.kotlin.restful.model.StudentResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface StudentService {

    fun save(createStudent: CreateStudent): Mono<StudentResponse>

    fun update(id: String, updateStudent: CreateStudent): Mono<StudentResponse>

    fun findById(id: String): Mono<StudentResponse>

    fun find(page: Int, size: Int): Flux<StudentResponse>

}