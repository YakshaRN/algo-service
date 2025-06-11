package com.emint.repo

import com.emint.data.StrategyDetailEntity
import com.emint.enum.StrategyStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface StrategyDetailRepo : JpaRepository<StrategyDetailEntity, Long> {

    @Query("SELECT amr FROM StrategyDetailEntity amr WHERE amr.id = :id")
    fun findByRequestId(id: UUID): StrategyDetailEntity
    fun findByUserIdAndStatus(userId: String, status: StrategyStatus): MutableList<StrategyDetailEntity>
}
