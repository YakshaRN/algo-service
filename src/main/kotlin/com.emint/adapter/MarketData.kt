package com.emint.adapter

import com.emint.config.FeignLoggingConfig
import com.emint.model.InstrumentDataRequest
import com.emint.model.InstrumentDataResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(
    value = "chartService",
    url = "https://dev.e-mint.in/chart",
    configuration = [FeignLoggingConfig::class]
)
interface MarketDataAdapter {

    @GetMapping(
        value = ["/charting/min"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun fetchData1Min(@RequestBody instrumentDataRequest: InstrumentDataRequest): InstrumentDataResponse

    @GetMapping(
        value = ["/charting/day"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun fetchData1day(@RequestBody instrumentDataRequest: InstrumentDataRequest): InstrumentDataResponse
}
