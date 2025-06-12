package com.emint.service

import com.emint.data.Action
import com.emint.enum.ActionStatus
import com.emint.enum.ConditionType
import com.emint.model.*
import com.emint.repo.StepActionRepo
import com.emint.repo.StrategyDetailRepo
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ActionMapper(
    private val strategyDetailRepo: StrategyDetailRepo,
    private val stepActionRepo: StepActionRepo,
    private val evaluateExpression: EvaluateExpression
) {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    fun parse(expression: String) {
        val cleaned = expression.replace("\\s+".toRegex(), "")
        val step = Action(strategyId = UUID.randomUUID(), stepNo = 0, condition = "Strike Selection", conditionType = ConditionType.When, actionStatus = ActionStatus.WAITING, nextStep = null)
        processExpression(cleaned, step)
    }

    private fun processExpression(expr: String, step: Action): Int {
        val (operatorIndex, operator) = findNextTopLevelOperator(expr) ?: run {
            val stepNo = stepCounter++
            steps.add(Step(stepNo, expr, ActionStatus.WAITING, null))
            return stepNo
        }

        val leftExpr = expr.substring(0, operatorIndex)
        val rightExpr = expr.substring(operatorIndex + 2)

        return when (operator) {
            "&&" -> {
                val leftStep = processExpression(leftExpr, null, steps)
                val rightStep = processExpression(rightExpr, null, steps)
                steps.first { it.stepNo == leftStep }.copy(nextStep = rightStep).also {
                    steps[steps.indexOfFirst { s -> s.stepNo == leftStep }] = it
                }
                leftStep
            }
            "||" -> {
                val leftStep = processExpression(leftExpr, null, steps)
                val rightStep = processExpression(rightExpr, null, steps)
                leftStep // OR condition is parallel so we evaluate both and continue with left
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

class ExpressionStepParser {

    private var stepCounter = 1

    fun parse(expression: String): List<Step> {
        val cleaned = expression.replace("\s+".toRegex(), "")
        val steps = mutableListOf<Step>()
        processExpression(cleaned, null, steps)
        println("First condition to evaluate: ${steps.firstOrNull()?.condition}")
        return steps
    }

    private fun processExpression(expr: String, parentStep: Int?, steps: MutableList<Step>): Int {
        val (operatorIndex, operator) = findNextTopLevelOperator(expr) ?: run {
            val stepNo = stepCounter++
            steps.add(Step(stepNo, expr, ActionStatus.WAITING, null))
            return stepNo
        }

        val leftExpr = expr.substring(0, operatorIndex)
        val rightExpr = expr.substring(operatorIndex + 2)

        return when (operator) {
            "&&" -> {
                val leftStep = processExpression(leftExpr, null, steps)
                val rightStep = processExpression(rightExpr, null, steps)
                steps.first { it.stepNo == leftStep }.copy(nextStep = rightStep).also {
                    steps[steps.indexOfFirst { s -> s.stepNo == leftStep }] = it
                }
                leftStep
            }
            "||" -> {
                val leftStep = processExpression(leftExpr, null, steps)
                val rightStep = processExpression(rightExpr, null, steps)
                leftStep // OR condition is parallel so we evaluate both and continue with left
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

fun main() {
    val expr = "(((MD(2,H) + 43.0 - 23.9) > 43.9) || (43.0 == 7 && 8 > 9))&& MD(C,3)>23"
    val parser = ExpressionStepParser()
    val steps = parser.parse(expr)
    steps.forEach { println(it) }
}
