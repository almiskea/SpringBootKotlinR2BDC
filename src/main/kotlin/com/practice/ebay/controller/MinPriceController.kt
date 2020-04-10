package com.practice.ebay.controller

import com.practice.ebay.service.MinPriceService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/price")
class MinPriceController(val minPriceService: MinPriceService) {

    @PutMapping("/{price}")
    suspend fun editPrice(@PathVariable price:Double) = minPriceService.editPrice(price)

    @GetMapping("/")
    suspend fun getPrice() = minPriceService.getPrice()
}