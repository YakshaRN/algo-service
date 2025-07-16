package com.emint.controller

import com.emint.data.ApiResponse
import com.emint.service.*
import com.emint.service.broker.KiteAuthService
import com.emint.service.broker.KiteDataService
import com.zerodhatech.kiteconnect.KiteConnect
import com.zerodhatech.models.HistoricalData
import org.slf4j.LoggerFactory
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*
import kotlin.jvm.java

@RestController
@RequestMapping("/broker")
class BrokerController(
    private val kiteConnect: KiteConnect,
    private val kiteDataService: KiteDataService,
    private val kiteAuthService: KiteAuthService
) {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @GetMapping("/login")
    fun getLoginUrl(): ResponseEntity<Void> {
        val loginUrl = kiteConnect.getLoginURL()
        log.info("Redirecting user to Kite login URL: {}", loginUrl)

        val headers = HttpHeaders()
        headers.location = URI.create(loginUrl)
        return ResponseEntity(headers, HttpStatus.FOUND)
    }

    @GetMapping("/callback")
    fun handleCallback(
        @RequestParam("request_token") requestToken: String
    ): ResponseEntity<ApiResponse<String>> {
        log.info("Received callback from Kite with request_token. $requestToken")

        // Delegate the token exchange and session setup to the service layer
        val user = kiteAuthService.generateSession(requestToken)

        log.info("Session successfully generated for user: {}", user)

        // In a real application, you would likely redirect to the frontend application
        // e.g., return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("/dashboard")).build()
        // For this example, we return a success message.
        val response = ApiResponse(
            message = "Successfully authenticated user: $user",
            data = "Login successful"
        )
        return ResponseEntity.ok(response)
    }

    @GetMapping("/historical")
    fun getHistoricalData(
        @RequestParam("instrument_token") instrumentToken: String,
        @RequestParam("interval") interval: String,
        @RequestParam("from_date")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        fromDate: Date,
        @RequestParam("to_date")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        toDate: Date,
        @RequestParam("continuous", required = false, defaultValue = "false") continuous: Boolean
    ): ResponseEntity<ApiResponse<List<HistoricalData>>> {
        val historicalData = kiteDataService.fetchHistoricalData(
            instrumentToken,
            fromDate,
            toDate,
            interval,
            continuous
        )

        val response = ApiResponse(
            message = "Successfully retrieved ${historicalData.size} data points.",
            data = historicalData
        )
        return ResponseEntity.ok(response)
    }
}
