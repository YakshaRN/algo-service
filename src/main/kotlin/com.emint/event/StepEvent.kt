package com.emint.event

import java.util.UUID

sealed class StepEvent {
    data class ExpressionEvaluated(val stepId: UUID) : StepEvent()

    data class StrikeSelectionRequested(val stepId: UUID) : StepEvent()

    data class OrderPlacementRequested(
        val strategyId: UUID,
        val condition: String
    ) : StepEvent()

    data class ExitOrderRequested(
        val strategyId: UUID,
        val condition: String
    ) : StepEvent()
}
