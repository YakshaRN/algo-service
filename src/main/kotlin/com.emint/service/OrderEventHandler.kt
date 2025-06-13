package com.emint.service

import com.emint.event.StepEvent
import com.emint.repo.ActionRepo
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class OrderEventHandler(
    private val actionRepo: ActionRepo,
    private val actionMapper: ActionMapper,
    private val instrumentService: InstrumentService,
    private val orderPlacement: OrderPlacement
) {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @EventListener
    fun handleExpression(event: StepEvent.ExpressionEvaluated) {
        try {
            log.info("Handling When case from ksql ${event.stepId}")
            val step = actionRepo.findById(event.stepId).get()
            actionMapper.processExpression(step)
        } catch (e: Exception) {
            log.error("Error handling strike selection: ${e.message}", e)
            throw e
        }
    }

    @EventListener
    fun handleStrikeSelection(event: StepEvent.StrikeSelectionRequested) {
        try {
            log.info("Handling strike selection for strategyId: ${event.stepId}")
            instrumentService.findInstrumentsForEngine(event.stepId)
        } catch (e: Exception) {
            log.error("Error handling strike selection: ${e.message}", e)
            throw e
        }
    }

    @EventListener
    fun handleOrderPlacement(event: StepEvent.OrderPlacementRequested) {
        try {
            log.info("Handling order placement for strategyId: ${event.strategyId}")
            orderPlacement.placeOrder(event.strategyId, event.condition)
        } catch (e: Exception) {
            log.error("Error handling order placement: ${e.message}", e)
            throw e
        }
    }

    @EventListener
    fun handleExitOrder(event: StepEvent.ExitOrderRequested) {
        try {
            log.info("Handling exit order for strategyId: ${event.strategyId}")
            orderPlacement.exitOrder(event.strategyId, event.condition)
        } catch (e: Exception) {
            log.error("Error handling exit order: ${e.message}", e)
            throw e
        }
    }
}
