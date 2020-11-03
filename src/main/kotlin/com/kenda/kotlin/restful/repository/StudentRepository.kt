package com.kenda.kotlin.restful.repository

import com.kenda.kotlin.restful.entity.Student
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface StudentRepository : ReactiveMongoRepository<Student, String> {

    fun existsByFirstName(firstName: String): Boolean

    fun findFirstByFullName(fullName: String): Mono<Student>

}