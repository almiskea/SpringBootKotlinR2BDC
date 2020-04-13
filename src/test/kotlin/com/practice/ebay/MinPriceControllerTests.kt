package com.practice.ebay

import com.google.gson.Gson
import com.practice.ebay.controller.MinPriceController
import com.practice.ebay.exceptions.CustomException
import com.practice.ebay.models.Price
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
class MinPriceControllerTests {

    lateinit var client: WebTestClient
    @Autowired
    lateinit var controller: MinPriceController
    var gson = Gson()
    @BeforeEach
    fun setup() {
        client = WebTestClient.bindToController(controller).build()
    }

    @Test
    fun changePrice_thenStatusShouldBeOk() {
        System.setProperty("min.price.value", "58.99")
        if(System.getProperty("min.price.value") != "58.99") throw CustomException("Not able to change the price")
        client.put()
                .uri("/price/70.89")
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .json(gson.toJson(Price(70.89)))
        if(System.getProperty("min.price.value") != "70.89") throw CustomException("Not able to change the price")
    }

    @Test
    fun getPrice_thenStatusShouldBeOk() {
        client.get()
                .uri("/price/")
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .json(gson.toJson(Price(System.getProperty("min.price.value").toDouble())))
    }
}