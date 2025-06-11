package com.emint.service

import com.emint.enum.StrategyStatus
import com.emint.model.*
import com.emint.repo.StrategyDetailRepo
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class StrategyValidation(val strategyDetailRepo: StrategyDetailRepo) {

    fun validateAndSaveStrategy(userId: String, strategyRequest: StrategyRequest): Boolean {
        if (validateEntryCondition(strategyRequest.entryConditions) &&
            validateStrikeSelection(strategyRequest.strikeSelection) &&
            validateExecutionSequence(strategyRequest.executionSequence) &&
            validateExitCondition(strategyRequest.exitConditions)
            ){
            val strategyDetailEntity = StrategyDetailEntity(
                timestamp = Instant.now().toEpochMilli(),
                userId = userId,
                strategyRequest = strategyRequest,
                status = StrategyStatus.CREATED
                )
            strategyDetailRepo.save(strategyDetailEntity)
            return true
        } else {
            return false
        }
    }

    private fun validateEntryCondition(strategyRequest: EntryConditions?): Boolean {
        return true
    }

    private fun validateStrikeSelection(strategyRequest: StrikeSelection?): Boolean {
        return true
    }

    private fun validateExecutionSequence(executionSequence: ExecutionSequence?): Boolean {
        return true
    }

    private fun validateExitCondition(exitConditions: ExitConditions?): Boolean {
        return true
    }
}
