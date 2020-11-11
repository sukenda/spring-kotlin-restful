package com.kenda.kotlin.restful.controller

import com.kenda.kotlin.restful.model.ApiResponse
import com.kenda.kotlin.restful.model.CreateStudent
import com.kenda.kotlin.restful.model.StudentResponse
import com.kenda.kotlin.restful.service.StudentService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/students")
class StudentController(val studentService: StudentService) {

    @PostMapping
    fun save(@RequestBody createStudent: CreateStudent): Mono<ApiResponse<StudentResponse>> {
        return studentService.save(createStudent)
                .flatMap { student ->
                    Mono.just(ApiResponse(
                            status = "Data berhasil disimpan",
                            code = HttpStatus.OK.value(),
                            data = student
                    ))
                }
    }

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: String, @RequestBody updateStudent: CreateStudent): Mono<ApiResponse<StudentResponse>> {
        return studentService.update(id, updateStudent)
                .flatMap { student ->
                    Mono.just(ApiResponse(
                            status = "Data berhasil diupdate",
                            code = HttpStatus.OK.value(),
                            data = student
                    ))
                }
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: String): Mono<ApiResponse<StudentResponse>> {
        return studentService.findById(id)
                .flatMap { student ->
                    Mono.just(ApiResponse(
                            status = "Data ditemukan",
                            code = HttpStatus.OK.value(),
                            data = student
                    ))
                }
    }

    @GetMapping
    fun find(@RequestParam("page") page: Int, @RequestParam("size") size: Int): Mono<ApiResponse<List<StudentResponse>>> {
        return studentService.find(page, size)
                .collectList()
                .flatMap { students ->
                    Mono.just(ApiResponse(
                            status = "Data berhasil diupdate",
                            code = HttpStatus.OK.value(),
                            data = students
                    ))
                }
    }

}