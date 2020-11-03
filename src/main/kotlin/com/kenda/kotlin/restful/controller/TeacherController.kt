package com.kenda.kotlin.restful.controller


import com.kenda.kotlin.restful.model.ApiResponse
import com.kenda.kotlin.restful.model.CreateTeacher
import com.kenda.kotlin.restful.model.TeacherResponse
import com.kenda.kotlin.restful.service.TeacherService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/teachers")
class TeacherController(val teacherService: TeacherService) {

    @PostMapping
    fun save(@RequestBody createTeacher: CreateTeacher): Mono<ApiResponse<TeacherResponse>> {
        return teacherService.save(createTeacher)
                .flatMap { student ->
                    Mono.just(ApiResponse(
                            status = "Data berhasil disimpan",
                            code = HttpStatus.OK.value(),
                            data = student
                    ))
                }
    }

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: String, @RequestBody updateTeacher: CreateTeacher): Mono<ApiResponse<TeacherResponse>> {
        return teacherService.update(id, updateTeacher)
                .flatMap { student ->
                    Mono.just(ApiResponse(
                            status = "Data berhasil diupdate",
                            code = HttpStatus.OK.value(),
                            data = student
                    ))
                }
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: String): Mono<ApiResponse<TeacherResponse>> {
        return teacherService.findById(id)
                .flatMap { student ->
                    Mono.just(ApiResponse(
                            status = "Data ditemukan",
                            code = HttpStatus.OK.value(),
                            data = student
                    ))
                }
    }

    @GetMapping
    fun find(@RequestParam("page") page: Int, @RequestParam("size") size: Int): Flux<TeacherResponse> {
        return teacherService.find(page, size)
    }

}