package com.emint.exception

import com.emint.data.ApiResponse
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @ExceptionHandler(KiteException::class)
    fun handleKiteException(ex: KiteException): ResponseEntity<ApiResponse<Nothing>> {
        log.error("Kite API Exception: Code=${ex.code}, Message=${ex.message}", ex)

        val status = when (ex.code) {
            403 -> HttpStatus.FORBIDDEN // Token exceptions (invalid, expired)
            400 -> HttpStatus.BAD_REQUEST // Bad request, invalid params
            404 -> HttpStatus.NOT_FOUND
            500 -> HttpStatus.INTERNAL_SERVER_ERROR // Internal server error at Kite
            503 -> HttpStatus.SERVICE_UNAVAILABLE // Service unavailable, maintenance
            504 -> HttpStatus.GATEWAY_TIMEOUT
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }

        val errorResponse = ApiResponse<Nothing>(
            success = false,
            message = "Kite API Error: ${ex.message}",
            data = null
        )

        return ResponseEntity.status(status).body(errorResponse)
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<ApiResponse<Nothing>> {
        log.error("An unexpected error occurred: ${ex.message}", ex)
        val errorResponse = ApiResponse<Nothing>(
            success = false,
            message = "An unexpected server error occurred.",
            data = null
        )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
    }
}
