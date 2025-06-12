package com.emint.model.emint

import com.fasterxml.jackson.annotation.JsonProperty

data class PositionsInUiRedisDto(
    @JsonProperty("data") val data: List<PositionsInUi>
)
