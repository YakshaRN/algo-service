package com.emint.model

data class InstrumentDataResponse(
    val instrument: String,
    val chartingData: List<List<Double>>
)
