package com.emint.data

import com.emint.enum.ActionStatus
import com.emint.enum.ConditionType
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import java.util.*

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "action")
data class Action(
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: UUID? = null,


    @Column(name = "condition_type")
    val conditionType: ConditionType? = null,

    @Column(name = "condition")
    val condition: String? = null,

    @Column(name = "action_status")
    val actionStatus: ActionStatus? = null,


    val nextStep: Int? = null
)