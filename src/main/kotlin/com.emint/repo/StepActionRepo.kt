package com.emint.repo

import com.emint.data.StepActionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository

interface StepActionRepo : JpaRepository<StepActionEntity, Long>{
    fun findByStrategyId(strategyId: UUID): MutableList<StepActionEntity>
    fun findByStrategyIdAndStepNo(strategyId: UUID, stepNo: Int): StepActionEntity?
}
