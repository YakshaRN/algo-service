package com.emint.data.hmds

import java.io.Serializable

data class CandleID(
    val symbolId: Int = 0,
    val timestamp: Int = 0
) : Serializable
