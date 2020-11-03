package com.kenda.kotlin.restful.service.impl

import com.kenda.kotlin.restful.entity.Teacher
import com.kenda.kotlin.restful.exception.DataNotFoundException
import com.kenda.kotlin.restful.model.CreateTeacher
import com.kenda.kotlin.restful.model.TeacherResponse
import com.kenda.kotlin.restful.repository.TeacherRepository
import com.kenda.kotlin.restful.service.TeacherService
import com.kenda.kotlin.restful.validation.ValidationUtil
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class TeacherServiceImpl(
        val repository: TeacherRepository,
        val validationUtil: ValidationUtil)
    : TeacherService {

    override fun save(createTeacher: CreateTeacher): Mono<TeacherResponse> {
        validationUtil.validate(createTeacher)

        val student = Teacher(
                id = UUID.randomUUID().toString(),
                firstName = createTeacher.firstName!!,
                lastName = createTeacher.lastName!!,
                fullName = createTeacher.fullName!!
        )

        return repository.save(student).flatMap {
            Mono.just(TeacherResponse(
                    id = student.id,
                    firstName = it.firstName,
                    lastName = it.lastName,
                    fullName = it.fullName)
            )
        }
    }

    override fun update(id: String, updateTeacher: CreateTeacher): Mono<TeacherResponse> {
        validationUtil.validate(updateTeacher)

        return repository.findById(id)
                .switchIfEmpty(Mono.error(DataNotFoundException("Data tidak ditemukan")))
                .flatMap { teacher ->
                    teacher.firstName = updateTeacher.firstName!!
                    teacher.lastName = updateTeacher.lastName!!
                    teacher.fullName = updateTeacher.fullName!!

                    repository.save(teacher)
                            .flatMap { updated ->
                                Mono.just(TeacherResponse(
                                        id = updated.id,
                                        firstName = updated.firstName,
                                        lastName = updated.lastName,
                                        fullName = updated.fullName))
                            }
                }
    }

    override fun findById(id: String): Mono<TeacherResponse> {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(DataNotFoundException("Data tidak ditemukan")))
                .flatMap { teacher ->
                    Mono.just(TeacherResponse(
                            id = teacher.id,
                            firstName = teacher.firstName,
                            lastName = teacher.lastName,
                            fullName = teacher.fullName)
                    )
                }
    }

    override fun find(page: Int, size: Int): Flux<TeacherResponse> {
        return repository.findAll()
                .flatMap { teacher ->
                    Mono.just(TeacherResponse(
                            id = teacher.id,
                            firstName = teacher.firstName,
                            lastName = teacher.lastName,
                            fullName = teacher.fullName))
                }
    }
}