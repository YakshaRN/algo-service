package com.emint.model

data class InstrumentDataRequest(
    val startTime: Long,
    val endTime: Long,
    val bucket: Int,
    val instrument: String
)
