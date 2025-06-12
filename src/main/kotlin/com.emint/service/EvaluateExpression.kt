package com.emint.service

import com.emint.config.ExpressionEvaluator
import com.emint.model.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class EvaluateExpression(private val functionDispatcher: FunctionDispatcher) {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
        private val functionCallRegex = Regex("""([A-Z]+)\(([^()]*)\)""")
    }

    fun evaluateExpression(rawExpression: String, symbol: String): Boolean {
        var expr = rawExpression
        functionCallRegex.findAll(rawExpression).forEach { match ->
            val full = match.value
            val functionName = match.groupValues[1]
            val paramString = match.groupValues[2]

            val params: List<Any> = paramString.split(",").map { it.trim() }.map { it.toIntOrNull() ?: it.removeSurrounding("\"") }
            val paramsIncludingSymbol = mutableListOf<Any>(symbol)
            paramsIncludingSymbol.addAll(params)

            val key = FunctionDispatcherKey(functionName, paramsIncludingSymbol.size)
            val function = functionDispatcher.functionDispatchMap[key]
                ?: throw IllegalArgumentException("No function for $functionName with ${params.size} params")

            val result = function(paramsIncludingSymbol)
            log.info("Evaluated $functionName(${params.joinToString()}) => $result")
            expr = expr.replaceFirst(full, result.toString())
        }
        log.info("Final expression to evaluate: $expr")
        return ExpressionEvaluator.evaluate(expr)
    }
}
