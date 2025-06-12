package com.emint.model.emint

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class LtpData(
    @JsonProperty("lpp") val lpp: String?,
    @JsonProperty("ltp") val ltp: Double
)
