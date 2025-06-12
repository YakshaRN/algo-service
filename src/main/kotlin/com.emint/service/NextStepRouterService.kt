package com.emint.service

import com.emint.constants.ApplicationConstants.Companion.EXIT_ORDER
import com.emint.constants.ApplicationConstants.Companion.ORDER_PLACEMENT
import com.emint.constants.ApplicationConstants.Companion.STRIKE_SELECTION
import com.emint.repo.ActionRepo
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class NextStepRouterService(
    val actionRepo: ActionRepo,
    val actionMapper: ActionMapper,
    val instrumentService: InstrumentService,
    val orderPlacement: OrderPlacement
) {

    fun processReceivedMessageForRouting(stepId: UUID) {
        val nextStep = actionRepo.findById(stepId).get()
        if (nextStep.condition == STRIKE_SELECTION) {
            // REMARK: strike selection--> mention of strike details in strategy leg & order execution
            instrumentService.findInstrumentsForEngine(nextStep.strategyId!!)
        } else if (nextStep.condition?.contains(ORDER_PLACEMENT) == true) {
            orderPlacement.placeOrder(nextStep.strategyId!!, nextStep.condition!!)
        } else if (nextStep.condition?.contains(EXIT_ORDER) == true) {
            orderPlacement.exitOrder(nextStep.strategyId!!, nextStep.condition!!)
        } else {
            actionMapper.processExpression(nextStep)
        }
    }
}
