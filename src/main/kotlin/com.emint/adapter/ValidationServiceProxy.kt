package com.emint.adapter

import com.emint.model.CreateOrderDTO
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(value = "validationService", url = "\${serviceUrl.validationServiceUrl}")
interface ValidationServiceProxy {

    @PostMapping(
        value = ["/api/order"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun placeOrder(@RequestHeader userId: String, @RequestBody data: List<CreateOrderDTO>)
}
