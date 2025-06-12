package com.emint.data.optionchain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class OptionChainResponse(
    @JsonProperty("ce")
    var ce: OptionChainDto?,
    @JsonProperty("pe")
    var pe: OptionChainDto?,
    @JsonProperty("strikePrice")
    var strikePrice: Double?
)
