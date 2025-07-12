package com.emint.repo

import com.emint.data.StrategyDetailEntity
import com.emint.enum.StrategyStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StrategyDetailRepo : JpaRepository<StrategyDetailEntity, Long> {

    fun findByRequestId(id: Long): StrategyDetailEntity
    fun findByUserIdAndStatus(userId: String, status: StrategyStatus): MutableList<StrategyDetailEntity>
}
