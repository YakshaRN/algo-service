package com.emint.service

import com.emint.model.*
import org.springframework.stereotype.Service

@Service
class NextStepRouterService(
    val strategyMapper: StepMapper
) {

    fun processReceivedMessageForRouting(messageReceived: KsqlReceivedMessage) {
        val messageUuid = messageReceived.strategyLegId

        val actionStep=strategyMapper.getStepByStrategyLegId(messageUuid)
        // Check for current step.
        val currentStep = strategyMapper.getStepByUuid(actionStep?.strategyId!!)

        // Validate Status

        // Evaluate map to next step.
        currentStep.forEach { it.status }

        // Call Next Step
    }
}
