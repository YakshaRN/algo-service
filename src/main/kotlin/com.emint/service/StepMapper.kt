package com.emint.service

import com.emint.data.Action
import com.emint.data.StepActionEntity
import com.emint.enum.StepName
import com.emint.enum.StepStatus
import com.emint.model.*
import com.emint.repo.ActionRepo
import com.emint.repo.StepActivityRepo
import com.emint.repo.StrategyDetailRepo
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class StepMapper(
    private val strategyDetailRepo: StrategyDetailRepo,
    private val stepActivityRepo: StepActivityRepo,
    private val actionRepo: ActionRepo,
    private val evaluateExpression: EvaluateExpression
) {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    fun populateSteps(strategyRequest: StrategyRequest) {
        val steps = mutableListOf<StepActionEntity>()
        evaluateEntryConditions(steps, strategyRequest.entryConditions)
        evaluateBrokerSymbols(steps, strategyRequest.strikeSelection)
        val executionSteps = evaluateExecutionOrder(strategyRequest.executionSequence, strategyRequest.exitConditions)
        steps.addAll(executionSteps)
    }

    fun getStepByUuid(strategyRequestId: UUID): MutableList<StepActionEntity> {
        val strategyAction = stepActivityRepo.findByStrategyId(strategyRequestId)
        return strategyAction
    }

    fun getStepByStrategyLegId(strategyLegId: UUID): Action? {
        val strategyAction = actionRepo.findByStrategyLegId(strategyLegId)
        return strategyAction
    }

    private fun evaluateEntryConditions(steps: MutableList<StepActionEntity>, entryConditions: EntryConditions?): StepActionEntity {
        // Separation Of If/When/TimeBased --> requires intelligence later
        val bool = evaluateExpression.evaluateExpression(entryConditions?.expression!!, entryConditions.brokerSymbol!!)
        println("bool: $bool")
        return StepActionEntity(UUID.randomUUID(), UUID.randomUUID(), StepName.ENTRY_CONDITION, 1, StepStatus.INITIATED)
        TODO("Not yet implemented")
    }

    private fun evaluateBrokerSymbols(steps: MutableList<StepActionEntity>, strikeSelection: StrikeSelection?): StepActionEntity {
        // list of --> BrokerSymbol
        TODO("Not yet implemented")
        return StepActionEntity(UUID.randomUUID(), UUID.randomUUID(), StepName.STRIKE_SELECTION, 1, StepStatus.INITIATED)
    }

    private fun evaluateExecutionOrder(executionSequence: ExecutionSequence?, exitConditions: ExitConditions?): MutableList<StepActionEntity> {
        // Strike <--> Side, Lot and Order
        // condition1 -> on Execution of L1|L2|L3|L4 --> global exit condition (step mapping)
        evaluateExitConditions(exitConditions)
        // condition2 -> on Execution of L1 --> send L2
        // condition3 -> on Execution of L2 --> exit condition (step mapping)
        evaluateExitConditions(exitConditions)
        // condition4 -> on Execution of L3 --> send L4
        // condition4 -> on Execution of L4 --> do nothing
        TODO("Not yet implemented")
        return mutableListOf()
    }

    private fun evaluateExitConditions(exitConditions: ExitConditions?): StepActionEntity {
        // Separation Of If/When/TimeBased --> requires intelligence later
        TODO("Not yet implemented")
        return StepActionEntity(UUID.randomUUID(), UUID.randomUUID(), StepName.EXIT_CONDITION, 1, StepStatus.INITIATED)
    }
}
