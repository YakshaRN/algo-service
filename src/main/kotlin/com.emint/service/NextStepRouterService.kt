package com.emint.service

import com.emint.constants.ApplicationConstants.Companion.EXIT_ORDER
import com.emint.constants.ApplicationConstants.Companion.ORDER_PLACEMENT
import com.emint.constants.ApplicationConstants.Companion.STRIKE_SELECTION
import com.emint.event.StepEvent
import com.emint.repo.ActionRepo
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class NextStepRouterService(
    private val actionRepo: ActionRepo,
    private val eventPublisher: ApplicationEventPublisher
) {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    fun processNextCondition(stepId: UUID) {
        try {
            log.info("Processing expression for stepId: $stepId")
            val nextStep = actionRepo.findById(stepId).get()
            when {
                nextStep.condition == STRIKE_SELECTION -> {
                    eventPublisher.publishEvent(StepEvent.StrikeSelectionRequested(nextStep.strategyId!!))
                }
                nextStep.condition?.contains(ORDER_PLACEMENT) == true -> {
                    eventPublisher.publishEvent(StepEvent.OrderPlacementRequested(nextStep.strategyId!!, nextStep.condition!!))
                }
                nextStep.condition?.contains(EXIT_ORDER) == true -> {
                    eventPublisher.publishEvent(StepEvent.ExitOrderRequested(nextStep.strategyId!!, nextStep.condition!!))
                }
                else -> {
                    eventPublisher.publishEvent(StepEvent.ExpressionEvaluated(stepId))
                }
            }
        } catch (e: Exception) {
            log.error("Error processing expression for stepId: $stepId: ${e.message}", e)
            throw e
        }
    }
}
