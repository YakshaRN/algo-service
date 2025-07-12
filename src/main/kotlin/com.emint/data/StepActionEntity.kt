package com.emint.data

import com.emint.enum.StepName
import com.emint.enum.StepStatus
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import java.util.*

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "step_action")
data class StepActionEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @Column(name = "strategy_id")
    var strategyId: Long?,

    @Column(name = "step_name")
    @Enumerated(EnumType.STRING)
    var stepName: StepName = StepName.ENTRY_CONDITION,

    @Column(name = "step_no")
    var stepNo: Int?,

    @Column(name = "status")
    var status: StepStatus?
)
