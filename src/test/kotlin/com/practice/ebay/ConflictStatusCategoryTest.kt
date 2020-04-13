package com.practice.ebay

import com.practice.ebay.controller.CategoryController
import com.practice.ebay.repositories.CategoryRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
class ConflictStatusCategoryTest {

    lateinit var client: WebTestClient
    @Autowired
    lateinit var categoryController: CategoryController
    @Autowired
    lateinit var categoryRepository : CategoryRepository

    @Test
    fun addCategory_thenStatusShouldBeConflict() {
        client = WebTestClient.bindToController(categoryController).build()
        var categoryList = categoryRepository.findAll().buffer(Int.MAX_VALUE).blockFirst()!!
        client.post()
                .uri("/categories/${categoryList.last().category}")
                .exchange()
                .expectStatus().is4xxClientError
    }
}