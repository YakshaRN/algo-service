package com.emint.model

import com.emint.type.OrderStatus
import java.util.UUID

data class KsqlReceivedMessage(
    val stepId: UUID,
    val status: OrderStatus?
)
