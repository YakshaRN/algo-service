package com.emint.repo

import com.emint.data.StepActionEntity
import com.emint.data.StrategyDetailEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StepActionRepo : JpaRepository<StepActionEntity, Long>{
    fun findByStrategyId(strategyId: Long): MutableList<StrategyDetailEntity>
    fun findByStrategyIdAndStepNo(strategyId: Long, stepNo: Long): StrategyDetailEntity?
}
