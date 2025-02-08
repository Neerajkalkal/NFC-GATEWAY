package com.example.nfcgateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NfcGatewayApplication

fun main(args: Array<String>) {
	runApplication<NfcGatewayApplication>(*args)
}
