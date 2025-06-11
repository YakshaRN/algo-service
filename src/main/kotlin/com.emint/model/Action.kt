package com.emint.model

import com.emint.enum.ActionStatus
import com.emint.enum.ConditionType
import jakarta.persistence.Embeddable

data class Action(
    val conditionType: ConditionType? = null,
    val condition: String? = null,
    val actionStatus: ActionStatus? = null,
    val nextStep: Int? = null // UUID karna chahiye
)