package com.credibanco.smartpos.payment

import com.credibanco.smartpos.payment.utils.test

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PaymentApplication

fun main(args: Array<String>) {
	runApplication<PaymentApplication>(*args)
    // test()
}
