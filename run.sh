#!/bin/bash

echo "Iniciando aplicación..."
java -cp "build/libs/*:libs/*" com.credibanco.smartpos.payment.PaymentApplication
