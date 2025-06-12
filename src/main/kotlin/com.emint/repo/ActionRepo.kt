package com.emint.repo

import com.emint.data.Action
import com.emint.data.StepActionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ActionRepo : JpaRepository<Action, UUID> {
    fun findByStrategyId(strategyId: UUID): MutableList<Action>
    fun findByStrategyLegId(strategyLegId: UUID): Action?
}
