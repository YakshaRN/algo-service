package com.emint.config

object ExpressionEvaluator {

    private val operatorPrecedence = mapOf(
        "(" to 0,
        ")" to 0,
        "||" to 1,
        "&&" to 2,
        "==" to 3,
        "!=" to 3,
        ">=" to 4,
        "<=" to 4,
        ">" to 4,
        "<" to 4,
        "+" to 5,
        "-" to 5,
        "*" to 6,
        "/" to 6,
        "%" to 6
    )

    private val binaryOps: Map<String, (Double, Double) -> Double> = mapOf(
        "+" to { a, b -> a + b },
        "-" to { a, b -> a - b },
        "*" to { a, b -> a * b },
        "/" to { a, b -> a / b },
        "%" to { a, b -> a % b }
    )

    private val comparisonOps: Map<String, (Double, Double) -> Boolean> = mapOf(
        ">" to { a, b -> a > b },
        "<" to { a, b -> a < b },
        ">=" to { a, b -> a >= b },
        "<=" to { a, b -> a <= b },
        "==" to { a, b -> a == b },
        "!=" to { a, b -> a != b }
    )

    private val logicalOps: Map<String, (Boolean, Boolean) -> Boolean> = mapOf(
        "&&" to { a, b -> a && b },
        "||" to { a, b -> a || b }
    )

    fun evaluate(expression: String): Boolean {
        val tokens = tokenize(expression)
        val rpn = toRPN(tokens)
        return evalRPN(rpn)
    }

    private fun tokenize(expression: String): List<String> {
        val regex = Regex(
            """(\d+\.\d+|\d+|[a-zA-Z_]+|[><=!]=|[><=]|[()*/%+-]|&&|\|\||==|!=)"""
        )
        return regex.findAll(expression.replace("\\s+".toRegex(), "")).map { it.value }.toList()
    }

    private fun toRPN(tokens: List<String>): List<String> {
        val output = mutableListOf<String>()
        val ops = ArrayDeque<String>()

        for (token in tokens) {
            when {
                token.toDoubleOrNull() != null -> output.add(token)
                token == "(" -> ops.addLast(token)
                token == ")" -> {
                    while (ops.isNotEmpty() && ops.last() != "(") {
                        output.add(ops.removeLast())
                    }
                    if (ops.isNotEmpty() && ops.last() == "(") ops.removeLast()
                }
                token in operatorPrecedence -> {
                    while (
                        ops.isNotEmpty() &&
                        operatorPrecedence[token]!! <= operatorPrecedence[ops.last()]!!
                    ) {
                        output.add(ops.removeLast())
                    }
                    ops.addLast(token)
                }
                else -> throw IllegalArgumentException("Unknown token: $token")
            }
        }
        while (ops.isNotEmpty()) output.add(ops.removeLast())
        return output
    }

    private fun evalRPN(rpn: List<String>): Boolean {
        val stack = ArrayDeque<Any>()
        for (token in rpn) {
            when {
                token.toDoubleOrNull() != null -> stack.addLast(token.toDouble())
                token in binaryOps -> {
                    val b = stack.removeLast() as Double
                    val a = stack.removeLast() as Double
                    stack.addLast(binaryOps[token]!!.invoke(a, b))
                }
                token in comparisonOps -> {
                    val b = stack.removeLast() as Double
                    val a = stack.removeLast() as Double
                    stack.addLast(comparisonOps[token]!!.invoke(a, b))
                }
                token in logicalOps -> {
                    val b = stack.removeLast() as Boolean
                    val a = stack.removeLast() as Boolean
                    stack.addLast(logicalOps[token]!!.invoke(a, b))
                }
                else -> throw IllegalArgumentException("Unknown operator in RPN: $token")
            }
        }
        return stack.removeLast() as Boolean
    }
}
