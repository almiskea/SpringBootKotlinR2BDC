package com.practice.ebay.service

import com.practice.ebay.models.Price
import org.springframework.stereotype.Service

@Service
class MinPriceService {

    suspend fun editPrice(price:Double): Price {
        //the price would logically be greater than 0
        // but here I am just checking for the right value, not the real business case
        System.setProperty("min.price.value", "${if(price > 0) price else 0}")
        return Price(System.getProperty("min.price.value")?.toDouble())
    }

    suspend fun getPrice() = mapOf("price" to System.getProperty("min.price.value")?.toDouble())
}