package com.emint.data

import com.emint.enum.ActionStatus
import com.emint.enum.StepName
import com.emint.enum.StepStatus
import com.emint.model.StrategyRequest
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.util.*

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "step_action")
data class StepActionEntity(

    @Id
    @Column(name = "id", nullable = false, unique = true)
    var id: UUID = UUID.randomUUID(),

    @Column(name = "strategy_id")
    var strategyId: UUID?,

    @Enumerated(EnumType.STRING)
    @Column(name = "step_name")
    var stepName: StepName=StepName.ENTRY_CONDITION,

    @Column(name = "step_no")
    var stepNo: Int?,

    @Column(name = "status")
    var status: StepStatus?
)