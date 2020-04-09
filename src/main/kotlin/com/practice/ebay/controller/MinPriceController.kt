package com.practice.ebay.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/price")
class MinPriceController {

    @PutMapping("/{price}")
    suspend fun editPrice(@PathVariable price:Double): Map<String, Double?> {
        //the price would logically be greater than 0
        // but here I am just checking for the right value, not the real business case

        System.setProperty("min.price.value", "${if(price > 0) price else 0}")
        return mapOf("price" to System.getProperty("min.price.value")?.toDouble())
    }

    @GetMapping("/")
    suspend fun getPrice() = mapOf("price" to System.getProperty("min.price.value")?.toDouble())
}