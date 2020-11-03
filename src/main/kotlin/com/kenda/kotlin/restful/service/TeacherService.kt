package com.kenda.kotlin.restful.service

import com.kenda.kotlin.restful.model.CreateTeacher
import com.kenda.kotlin.restful.model.TeacherResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface TeacherService {

    fun save(createTeacher: CreateTeacher): Mono<TeacherResponse>

    fun update(id: String, updateTeacher: CreateTeacher): Mono<TeacherResponse>

    fun findById(id: String): Mono<TeacherResponse>

    fun find(page: Int, size: Int): Flux<TeacherResponse>

}