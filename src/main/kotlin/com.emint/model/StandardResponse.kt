package com.emint.model

data class StandardResponse(
    var status: Boolean,
    var message: String?,
    var data: Any?,
    var statusCode: String = "200"
) {
    constructor() : this(status = true, message = "success", data = null)
}
