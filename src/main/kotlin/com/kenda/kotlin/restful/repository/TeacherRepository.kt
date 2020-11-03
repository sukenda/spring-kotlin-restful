package com.kenda.kotlin.restful.repository

import com.kenda.kotlin.restful.entity.Teacher
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TeacherRepository : ReactiveMongoRepository<Teacher, String> {

}