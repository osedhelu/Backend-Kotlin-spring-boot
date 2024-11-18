package com.credibanco.smartpos.payment.dto

data class AnnulmentRequest(
        val receiptId: String,
        val rrn: String
)
