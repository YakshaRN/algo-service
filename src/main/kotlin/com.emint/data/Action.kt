package com.emint.data

import com.emint.enum.ActionStatus
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import java.util.*

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "action")
data class Action(
    @Id
    @Column(name = "id", nullable = false, unique = true)
    val id: UUID = UUID.randomUUID(),

    @Column(name = "strategy_id")
    var strategyId: UUID?,

    @Column(name = "condition")
    var condition: String? = null,

    @Column(name = "action_status")
    val actionStatus: ActionStatus? = null,

    @Column(name = "next_step")
    val nextStep: UUID? = null
)
