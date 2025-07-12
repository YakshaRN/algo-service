package com.emint.data

import com.emint.enum.ActionStatus
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "action")
data class Action(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(name = "strategy_id")
    var strategyId: Long?,

    @Column(name = "condition")
    var condition: String? = null,

    @Column(name = "action_status")
    @Enumerated(EnumType.STRING)
    var actionStatus: ActionStatus? = null,

    @Column(name = "evaluation_status")
    var evaluationStatus: Boolean = false,

    @Column(name = "next_step")
    val nextStep: Long? = null
)
