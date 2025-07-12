package com.emint.model

import com.emint.type.OrderStatus

data class KsqlReceivedMessage(
    val stepId: Long,
    val status: OrderStatus?
)
