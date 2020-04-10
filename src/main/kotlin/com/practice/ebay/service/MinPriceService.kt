package com.practice.ebay.service

import org.springframework.stereotype.Service

@Service
class MinPriceService {

    suspend fun editPrice(price:Double): Map<String, Double?> {
        //the price would logically be greater than 0
        // but here I am just checking for the right value, not the real business case
        System.setProperty("min.price.value", "${if(price > 0) price else 0}")
        return mapOf("price" to System.getProperty("min.price.value")?.toDouble())
    }

    suspend fun getPrice() = mapOf("price" to System.getProperty("min.price.value")?.toDouble())
}