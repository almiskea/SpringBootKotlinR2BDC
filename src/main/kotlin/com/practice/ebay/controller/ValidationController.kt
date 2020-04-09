package com.practice.ebay.controller

import com.practice.ebay.exceptions.NotEligibleForShippingException
import com.practice.ebay.service.ValidationService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/validation")
class ValidationController(val validationService: ValidationService) {

    @GetMapping("/validate")
    suspend fun validate(@RequestParam title :String,
                         @RequestParam seller :String,
                         @RequestParam category :Int,
                         @RequestParam price :Double): Map<String, Boolean> =
            validationService.validate(seller,category,price,System.getProperty("min.price.value")?.toDouble())?.let { mapOf("eligible" to true) } ?:
            throw NotEligibleForShippingException("The item is not eligible for the shipping service")

}