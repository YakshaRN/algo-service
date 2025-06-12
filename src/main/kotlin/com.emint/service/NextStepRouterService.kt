package com.emint.service

import com.emint.data.StrategyDetailEntity
import com.emint.enum.StrategyStatus
import com.emint.model.*
import com.emint.repo.StrategyDetailRepo
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class NextStepRouterService(
    val strategyMapper: StepMapper
) {


    fun processReceivedMessageForRouting(messageReceived: KsqlReceivedMessage) {
        val messageUuid=messageReceived.strategyLegId
        //Check for current step.
        val currentStep=strategyMapper.getStepByUuid(messageUuid)

        //Validate Status


        //Evaluate map to next step.
        currentStep.forEach { it.status }

        //Call Next Step
    }
}
