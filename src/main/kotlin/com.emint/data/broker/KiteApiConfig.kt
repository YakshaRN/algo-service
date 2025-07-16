package com.emint.data.broker

import jakarta.validation.constraints.NotBlank
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@ConfigurationProperties(prefix = "kite.api")
@Validated
data class KiteApiConfig(
    @field:NotBlank
    val key: String,

    @field:NotBlank
    val secret: String,

    @field:NotBlank
    val redirectUrl: String
)
