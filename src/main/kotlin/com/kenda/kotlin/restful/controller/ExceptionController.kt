package com.kenda.kotlin.restful.controller

import com.kenda.kotlin.restful.exception.DataNotFoundException
import com.kenda.kotlin.restful.model.ApiResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class ExceptionController {

    @ExceptionHandler(value = [DataNotFoundException::class])
    fun runtimeErrorHandler(runtimeException: DataNotFoundException): ApiResponse<String> {
        return ApiResponse(
                code = 400,
                status = "BAD REQUEST",
                data = runtimeException.message!!
        )
    }

    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun validationHandler(constraintViolationException: ConstraintViolationException): ApiResponse<String> {
        return ApiResponse(
                code = 400,
                status = "BAD REQUEST",
                data = constraintViolationException.message!!
        )
    }

}