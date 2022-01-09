package com.mvnikitin.reststudy.rest

import com.mvnikitin.reststudy.exception.ResourceNotFoundException
import com.mvnikitin.reststudy.extension.requestId
import com.mvnikitin.reststudy.model.ErrorDto
import org.apache.logging.log4j.LogManager
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingRequestHeaderException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class AnimalsControllerAdvice() {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(request: HttpServletRequest, ex: ResourceNotFoundException) =
        handleError(request, ex, HttpStatus.NOT_FOUND)

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(request: HttpServletRequest, ex: HttpMessageNotReadableException): ResponseEntity<ErrorDto> =
        handleError(request, ex, HttpStatus.BAD_REQUEST)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(request: HttpServletRequest, ex: MethodArgumentNotValidException): ResponseEntity<ErrorDto> =
        handleError(request, ex, HttpStatus.BAD_REQUEST)

    @ExceptionHandler(MissingRequestHeaderException::class)
    fun handleMissingRequestHeaderException(request: HttpServletRequest, ex: MissingRequestHeaderException): ResponseEntity<ErrorDto> =
        handleError(request, ex, HttpStatus.BAD_REQUEST)

    @ExceptionHandler(Throwable::class)
    fun handleOtherExceptions(request: HttpServletRequest, ex: Throwable): ResponseEntity<ErrorDto> =
        handleError(request, ex, HttpStatus.INTERNAL_SERVER_ERROR)

    private fun handleError(request: HttpServletRequest, ex: Throwable, status: HttpStatus): ResponseEntity<ErrorDto> {
        val message = "Request ID=${request.requestId()} exception occured: ${ex.message}"
        log.error(message)
        return ResponseEntity(
            ErrorDto(
                requestId = request.requestId() ?: "",
                url = request.requestURL.toString(),
                message = message,
                code = status.value(),
            ),
            status
        )
    }

    companion object {
        private val log = LogManager.getLogger(AnimalsControllerAdvice::class.java)
    }
}