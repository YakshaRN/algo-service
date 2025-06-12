package com.emint.repo

import com.emint.data.StrategyLegEntity
import com.emint.type.OrderStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface StrategyLegRepo : JpaRepository<StrategyLegEntity, UUID> {
    fun findByStrategyIdAndLegName(id: UUID, legName: String): StrategyLegEntity
    fun findByStrategyIdAndLegNameIn(strategyId: UUID, legNames: List<String>): List<StrategyLegEntity>
    fun countByStrategyIdAndStatus(strategyId: UUID, status: OrderStatus = OrderStatus.COMPLETE): Int
}
