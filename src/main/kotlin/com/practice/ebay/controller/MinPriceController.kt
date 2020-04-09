package com.practice.ebay.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/price")
class MinPriceController {

    @PutMapping("/{price}")
    suspend fun editPrice(@PathVariable price:Double): Map<String, Double?> {
        System.setProperty("min.price.value", "${price}")
        return mapOf("price" to System.getProperty("min.price.value")?.toDouble())
    }

    @GetMapping("/")
    suspend fun getPrice() = mapOf("price" to System.getProperty("min.price.value")?.toDouble())
}