package com.kenda.kotlin.restful.service.impl

import com.kenda.kotlin.restful.entity.Student
import com.kenda.kotlin.restful.exception.DataNotFoundException
import com.kenda.kotlin.restful.model.CreateStudent
import com.kenda.kotlin.restful.model.StudentResponse
import com.kenda.kotlin.restful.repository.StudentRepository
import com.kenda.kotlin.restful.service.StudentService
import com.kenda.kotlin.restful.validation.ValidationUtil
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class StudentServiceImpl(val repository: StudentRepository,
                         val validationUtil: ValidationUtil) : StudentService {

    override fun save(createStudent: CreateStudent): Mono<StudentResponse> {
        validationUtil.validate(createStudent)

        val student = Student(
                id = UUID.randomUUID().toString(),
                firstName = createStudent.firstName!!,
                lastName = createStudent.lastName!!,
                fullName = createStudent.fullName!!
        )

        return repository.save(student).flatMap {
            Mono.just(StudentResponse(
                    id = student.id,
                    firstName = it.firstName,
                    lastName = it.lastName,
                    fullName = it.fullName)
            )
        }
    }

    override fun update(id: String, updateStudent: CreateStudent): Mono<StudentResponse> {
        validationUtil.validate(updateStudent)

        return repository.findById(id)
                .switchIfEmpty(Mono.error(DataNotFoundException("Data tidak ditemukan")))
                .flatMap { student ->
                    student.firstName = updateStudent.firstName!!
                    student.lastName = updateStudent.lastName!!
                    student.fullName = updateStudent.fullName!!

                    repository.save(student)
                            .flatMap { updated ->
                                Mono.just(StudentResponse(
                                        id = updated.id,
                                        firstName = updated.firstName,
                                        lastName = updated.lastName,
                                        fullName = updated.fullName))
                            }
                }
    }

    override fun findById(id: String): Mono<StudentResponse> {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(DataNotFoundException("Data tidak ditemukan")))
                .flatMap { student ->
                    Mono.just(StudentResponse(
                            id = student.id,
                            firstName = student.firstName,
                            lastName = student.lastName,
                            fullName = student.fullName)
                    )
                }
    }

    override fun find(page: Int, size: Int): Flux<StudentResponse> {
        return repository.findAll()
                .flatMap { student ->
                    Mono.just(StudentResponse(
                            id = student.id,
                            firstName = student.firstName,
                            lastName = student.lastName,
                            fullName = student.fullName))
                }
    }
}