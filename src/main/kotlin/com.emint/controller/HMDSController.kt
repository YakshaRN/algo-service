package com.emint.controller

import com.emint.model.StandardResponse
import com.emint.repo.LocalSymbolIDRepo
import com.emint.repo.MinuteEquityCandlesRepo
import com.emint.repo.MinuteFOCandlesV2Repo
import com.emint.service.*
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.jvm.java

@RestController
@RequestMapping("/hmds")
class HMDSController(
    val minuteEquityCandlesRepo: MinuteEquityCandlesRepo,
    val minuteFOCandlesV2Repo: MinuteFOCandlesV2Repo,
    val localSymbolIDRepo: LocalSymbolIDRepo
) {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @PostMapping("/check")
    fun addStrategy(@RequestHeader("symbol") symbol: String): ResponseEntity<Any> {
        log.info("Strategy validation for symbol: $symbol")
        var body = StandardResponse()
        var status = HttpStatus.OK
        try {
            val symbolsList = localSymbolIDRepo.findBySymbolIn(setOf("ITC")).map { it.id!! }.toList()
//            val validatedResponse = minuteFOCandlesV2Repo.fetchAllDataForGivenTimestampAndSymbolIDs(1752228840, symbolsList.toSet())

            val validatedResponse = minuteFOCandlesV2Repo.fetchAllCandlesWithinGivenTSForGivenSymbolIDs(symbolsList, 1752228840, 1752488880)
            body = StandardResponse(status = true, message = "Strategy request created successfully.", validatedResponse)
        } catch (t: Throwable) {
            status = HttpStatus.BAD_REQUEST
            body.message = t.message
        }
        return ResponseEntity.status(status).body(body)
    }
}
