package com.practice.ebay

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EbayApplication

fun main(args: Array<String>) {
	runApplication<EbayApplication>(*args)
}
