package com.emint.service

import com.emint.adapter.ValidationServiceProxy
import com.emint.data.StrategyLegEntity
import com.emint.model.*
import com.emint.repo.StrategyLegRepo
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class OrderPlacement(
    private val strategyLegRepo: StrategyLegRepo,
    private val validationServiceProxy: ValidationServiceProxy
) {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    fun placeOrder(strategyId: Long, expression: String) {
        val parts = expression.split(" ")
        val legName = parts[1] // "Leg1"
        val strategyLeg = strategyLegRepo.findByStrategyIdAndLegName(strategyId, legName)
        val order = createOrder(strategyLeg)
        validationServiceProxy.placeOrder("userId", order)
    }

    fun exitOrder(strategyId: Long, expression: String) {
        val parts = expression.split(" ")
        val arrayOfLegs = parts[1].split(",") // "Leg1"
        val strategyLegs = strategyLegRepo.findByStrategyIdAndLegNameIn(strategyId, arrayOfLegs)
        strategyLegs.map { strategyLeg ->
            val order = createExitOrder(strategyLeg)
            validationServiceProxy.placeOrder("userId", order)
        }
    }

    private fun createOrder(strategyLeg: StrategyLegEntity): MutableList<CreateOrderDTO> {
        val meta: Map<String, Any> = mapOf(
            "legName" to strategyLeg.legName,
            "strategyId" to strategyLeg.strategyId,
            "condition" to "entry"
        )
        return mutableListOf(CreateOrderDTO(strategyLeg, meta))
    }

    private fun createExitOrder(strategyLeg: StrategyLegEntity): List<CreateOrderDTO> {
        val meta: Map<String, Any> = mapOf(
            "legName" to strategyLeg.legName,
            "strategyId" to strategyLeg.strategyId,
            "condition" to "exit"
        )
        // place exit market order
        return mutableListOf(CreateOrderDTO(strategyLeg, meta))
    }
}
