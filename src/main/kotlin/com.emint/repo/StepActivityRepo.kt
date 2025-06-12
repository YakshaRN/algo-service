package com.emint.repo

import com.emint.data.StepActionEntity
import com.emint.data.StrategyDetailEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StepActionRepo : JpaRepository<StepActionEntity, UUID>{
//    fun findByStrategyId(strategyId: UUID): MutableList<StrategyDetailEntity>
//    fun findByStrategyIdAndStepNo(strategyId: UUID, stepNo: Int): StrategyDetailEntity?
}
