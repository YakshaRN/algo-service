package com.emint.event

sealed class StepEvent {
    data class ExpressionEvaluated(val stepId: Long) : StepEvent()

    data class StrikeSelectionRequested(val stepId: Long) : StepEvent()

    data class OrderPlacementRequested(
        val strategyId: Long,
        val condition: String
    ) : StepEvent()

    data class ExitOrderRequested(
        val strategyId: Long,
        val condition: String
    ) : StepEvent()
}
