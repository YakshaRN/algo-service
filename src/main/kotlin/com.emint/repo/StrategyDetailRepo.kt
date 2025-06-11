package com.emint.repo

import com.emint.data.StrategyDetailEntity
import com.emint.enum.StrategyStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface StrategyDetailRepo : JpaRepository<StrategyDetailEntity, UUID> {

    fun findByRequestId(id: UUID): StrategyDetailEntity
    fun findByUserIdAndStatus(userId: String, status: StrategyStatus): MutableList<StrategyDetailEntity>
}
