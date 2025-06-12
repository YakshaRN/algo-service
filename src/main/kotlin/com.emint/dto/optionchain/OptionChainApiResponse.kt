package com.emint.data.optionchain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class OptionChainApiResponse(
    @JsonProperty("underlying")
    var underlying: OptionChainDto?,
    @JsonProperty("optionChain")
    var optionChain: List<OptionChainResponse>?,
)
