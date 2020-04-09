package com.practice.ebay

import com.practice.ebay.controller.MinPriceController
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

    @BeforeEach
    fun setup() {
        client = WebTestClient.bindToController(controller).build()
    }

    @Test
    fun changePrice_thenStatusShouldBeOk() {
        client.put()
                .uri("/price/70")
                .exchange()
                .expectStatus().isOk
    }

    @Test
    fun getPrice_thenStatusShouldBeOk() {
        client.get()
                .uri("/price/")
                .exchange()
                .expectStatus().isOk
    }
}