package com.emint.model

import com.emint.enum.ActionStatus
import com.emint.enum.ConditionType

data class Action(
    val conditionType: ConditionType?,
    val condition: String?,
    val actionStatus: ActionStatus?,
    val nextStep: Int?
)
