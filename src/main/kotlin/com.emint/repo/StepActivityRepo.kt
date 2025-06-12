package com.emint.repo

import com.emint.data.StepActionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StepActivityRepo : JpaRepository<StepActionEntity, UUID> {
    fun findByStrategyId(strategyId: UUID): MutableList<StepActionEntity>
    fun findByStrategyIdAndStepNo(strategyId: UUID, stepNo: Int): StepActionEntity?
}
