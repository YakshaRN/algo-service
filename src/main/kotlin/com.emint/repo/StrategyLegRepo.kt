package com.emint.repo

import com.emint.data.StrategyLegEntity
import com.emint.type.OrderStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StrategyLegRepo : JpaRepository<StrategyLegEntity, Long> {
    fun findByStrategyIdAndLegName(id: Long, legName: String): StrategyLegEntity
    fun findByStrategyIdAndLegNameIn(strategyId: Long, legNames: List<String>): List<StrategyLegEntity>
    fun countByStrategyIdAndStatus(strategyId: Long, status: OrderStatus = OrderStatus.COMPLETE): Int
}
