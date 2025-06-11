package com.emint.data

import com.emint.enum.ActionStatus
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
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

    @Column(name = "step_no")
    var stepNo: Int?,

    @Column(name = "status")
    var status: ActionStatus?
)