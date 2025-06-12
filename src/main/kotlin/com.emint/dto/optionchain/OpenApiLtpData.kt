package com.emint.data.optionchain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class OpenApiLtpData(
    @JsonProperty("open")
    var open: Double?,
    @JsonProperty("ltp")
    var ltp: Double?,
    @JsonProperty("prevClose")
    var prevClose: Double?,
    @JsonProperty("high")
    var high: Double?,
    @JsonProperty("low")
    var low: Double?,
    @JsonProperty("dprH")
    var dprH: Double?,
    @JsonProperty("dprL")
    var dprL: Double?
) {
    constructor() : this(
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0
    )
}
