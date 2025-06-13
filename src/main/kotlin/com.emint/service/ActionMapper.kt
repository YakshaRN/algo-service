package com.emint.service

import com.emint.data.Action
import com.emint.enum.ActionStatus
import com.emint.repo.ActionRepo
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ActionMapper(
    private val nextStepRouterService: NextStepRouterService,
    private val evaluateExpression: EvaluateExpression,
    private val actionRepo: ActionRepo
) {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    fun processExpression(step: Action) {
        val expr = step.condition!!
        val (operatorIndex, operator) = findNextTopLevelOperator(expr) ?: run {
            if (evaluateExpression.evaluateExpression(expr)) {
                nextStepRouterService.processNextCondition(step.nextStep!!)
            }
            return
        }

        val leftExpr = expr.substring(0, operatorIndex)
        val rightExpr = expr.substring(operatorIndex + 2)

        return when (operator) {
            "&&" -> {
                step.condition = rightExpr
                val prevStep = Action(strategyId = UUID.randomUUID(), condition = leftExpr, actionStatus = ActionStatus.WAITING, nextStep = step.id)
                actionRepo.save(step)
                actionRepo.save(prevStep)
                processExpression(prevStep)
            }
            "||" -> {
                step.condition = rightExpr
                val parallelStep = Action(strategyId = UUID.randomUUID(), condition = leftExpr, actionStatus = ActionStatus.WAITING, nextStep = step.nextStep)
                actionRepo.save(step)
                actionRepo.save(parallelStep)
                processExpression(parallelStep)
                processExpression(step)
            }
            else -> throw IllegalStateException("Unknown operator $operator")
        }
    }

    private fun findNextTopLevelOperator(expression: String): Pair<Int, String>? {
        var depth = 0
        var i = 0
        while (i < expression.length - 1) {
            when (expression[i]) {
                '(' -> depth++
                ')' -> depth--
                '&', '|' -> {
                    if (depth == 0 && expression[i] == expression[i + 1]) {
                        val op = expression.substring(i, i + 2)
                        return i to op
                    }
                }
            }
            i++
        }
        return null
    }
}
