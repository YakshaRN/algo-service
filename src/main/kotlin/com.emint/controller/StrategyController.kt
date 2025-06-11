package com.emint.controller

import com.emint.model.StandardResponse
import com.emint.model.StrategyRequest
import com.emint.service.*
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/strategy")
class StrategyController(
    val strategyValidation: StrategyValidation,
    val stepMapper: StepMapper
) {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @PostMapping("/add-strategy")
    fun addStrategy(@RequestHeader("userId") userId: String, @RequestBody strategyRequest: StrategyRequest): ResponseEntity<Any> {
        log.info("Strategy validation for user: $userId, strategyRequest: $strategyRequest")
        var body = StandardResponse()
        var status = HttpStatus.OK
        try {
            val validatedResponse = strategyValidation.validateAndSaveStrategy(userId, strategyRequest)
            body = StandardResponse(status = true, message = "Strategy request created successfully.", validatedResponse)
        } catch (t: Throwable) {
            status = HttpStatus.BAD_REQUEST
            body.message = t.message
        }
        return ResponseEntity.status(status).body(body)
    }

    @PostMapping("/deploy-strategy")
    fun deployStrategy(@RequestHeader("userId") userId: String, @RequestBody strategyRequestId: UUID):
            ResponseEntity<Any> {
        log.info("Strategy validation for user: $userId, strategyRequest: $strategyRequestId")
        var body = StandardResponse()
        var status = HttpStatus.OK
        try {
            val validatedResponse = stepMapper.populateSteps(strategyRequestId)
            body = StandardResponse(status = true, message = "Strategy request created successfully.", validatedResponse)
        } catch (t: Throwable) {
            status = HttpStatus.BAD_REQUEST
            body.message = t.message
        }
        return ResponseEntity.status(status).body(body)
    }
}
