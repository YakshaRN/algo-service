package com.emint.data

data class ApiResponse<T>(
    val success: Boolean = true,
    val message: String,
    val data: T?
)
