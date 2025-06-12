package com.emint.model

import com.emint.type.OrderStatus
import java.util.UUID

data class KsqlReceivedMessage(
    val strategyLegId: UUID,
    val status: OrderStatus
)
