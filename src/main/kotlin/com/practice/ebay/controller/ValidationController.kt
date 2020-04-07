package com.practice.ebay.controller

import com.practice.ebay.exceptions.NotEligibleForShippingException
import com.practice.ebay.models.Username
import com.practice.ebay.service.ValidationService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/")
class ValidationController(val validationService: ValidationService) {

    @GetMapping("/validate")
    suspend fun validate(@RequestParam title :String,
                         @RequestParam seller :String,
                         @RequestParam category :Int,
                         @RequestParam price :Double): Map<String, Boolean> =
            validationService.validate(seller,category,price)?.let { mapOf("eligible" to true) } ?:
            throw NotEligibleForShippingException("The item is not eligible for the shipping service")

    @GetMapping("/")
    suspend fun health() = mapOf("Status" to "UP")
}