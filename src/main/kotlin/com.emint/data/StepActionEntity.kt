package com.emint.data

import com.emint.enum.ActionStatus
import com.emint.model.Action
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "step_action")
data class StepActionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "strategy_id")
    var strategyId: Long?,

    @Column(name = "step_no")
    var stepNo: Int?,

    @Column(name = "actions")
    var actions: MutableList<Action>,

    @Column(name = "status")
    var status: ActionStatus?
)
