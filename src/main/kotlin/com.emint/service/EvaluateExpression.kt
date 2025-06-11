package com.emint.service

import com.emint.functionPattern.StringFunctionMapper.Companion.functionRegexMap
import com.emint.model.*
import com.emint.repo.StepActionRepo
import com.emint.repo.StrategyDetailRepo
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.script.ScriptEngineManager

@Service
class EvaluateExpression(
    private val strategyDetailRepo: StrategyDetailRepo,
    private val stepActionRepo: StepActionRepo
) {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    fun evaluateConditions(entryConditions: EntryConditions) {
        val input = entryConditions.expression!!
        val result = evaluateExpression(input)
        println("Final Result: $result")
    }

    fun evaluateExpression(expression: String): Boolean {
        val allPatterns = functionRegexMap.values.map { it.pattern }.joinToString("|", "(", ")")
        val combinedRegex = Regex(allPatterns)

        var evaluatedExpression = expression

        combinedRegex.findAll(expression).forEach { match ->
            val condition = match.value
            val result = evaluateCondition(condition)
            evaluatedExpression = evaluatedExpression.replace(condition, result.toString())
        }

        return evalBooleanExpression(evaluatedExpression)
    }

    fun evaluateCondition(condition: String): Boolean {
        for ((functionName, regex) in functionRegexMap) {
            val match = regex.matchEntire(condition)
            if (match != null) {
                val field = match.groupValues[1]
                val offset = match.groupValues[2].toInt()
                val operator = match.groupValues[3]
                val threshold = match.groupValues[4].toDouble()

                val value = when (functionName) {
                    "MD" -> resolveMD(field, offset)
                    "GD" -> resolveGD(field, offset)
                    else -> throw IllegalArgumentException("No resolver for $functionName")
                }

                return compare(value, operator, threshold)
            }
        }

        throw IllegalArgumentException("No match found for condition: $condition")
    }

    fun resolveMD(field: String, offset: Int): Double {
        return when (field) {
            "O" -> 45.0 + offset  // Example value
            "C" -> 52.0 + offset
            else -> 0.0
        }
    }

    fun resolveGD(field: String, offset: Int): Double {
        return when (field) {
            "OI" -> 25.0 + offset
            else -> 0.0
        }
    }

    fun compare(value: Double, operator: String, threshold: Double): Boolean {
        return when (operator) {
            ">" -> value > threshold
            "<" -> value < threshold
            ">=" -> value >= threshold
            "<=" -> value <= threshold
            "==" -> value == threshold
            "!=" -> value != threshold
            else -> throw IllegalArgumentException("Unknown operator: $operator")
        }
    }

    fun evalBooleanExpression(expression: String): Boolean {
        val engine = ScriptEngineManager().getEngineByName("JavaScript")
        return engine.eval(expression) as Boolean
    }

}
