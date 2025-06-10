package com.emint.model

data class StrategyRequest(
    val entryConditions: EntryConditions?,
    val strikeSelection: StrikeSelection?,
    val executionSequence: ExecutionSequence?,
    val exitConditions: ExitConditions?
)
