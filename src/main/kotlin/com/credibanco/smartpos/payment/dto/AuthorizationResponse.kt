package com.credibanco.smartpos.payment.dto

data class AuthorizationResponse(
        val receiptId: String?,
        val rrn: String?,
        val statusCode: String,
        val statusDescription: String
)
