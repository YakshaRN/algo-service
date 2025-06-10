package com.emint.model

import com.emint.enum.Criteria
import com.emint.enum.ExpiryOffset
import com.emint.enum.ExpiryType
import com.emint.enum.Underlying
import com.emint.enum.Segment

data class StrikeSelection(
    val underlying : Underlying?,
    val segment: Segment,
    val expiryType : ExpiryType?,
    val expiryOffset: ExpiryOffset?,
    val criteria: Criteria?,
    val value: Double?,
    val expression: String?
)
