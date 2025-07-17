package com.emint.repo

import com.emint.data.StepActionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StepActivityRepo : JpaRepository<StepActionEntity, Long> {
    fun findByStrategyId(strategyId: Long): MutableList<StepActionEntity>
    fun findByStrategyIdAndStepNo(strategyId: Long, stepNo: Int): StepActionEntity?
}
