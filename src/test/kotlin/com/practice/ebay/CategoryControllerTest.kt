package com.practice.ebay

import com.practice.ebay.controller.CategoryController
import com.practice.ebay.repositories.CategoryRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
class CategoryControllerTest {

    lateinit var client: WebTestClient
    @Autowired
    lateinit var controller: CategoryController
    @Autowired
    lateinit var categoryRepository : CategoryRepository
    var category = 46
    var categoryToGet = 0
    var categoryId = 1

    @BeforeEach
    fun setup() {
        client = WebTestClient.bindToController(controller).build()
        var categoryTemp = categoryRepository.findAll().blockLast()
        categoryId = categoryTemp?.id!!
        categoryToGet = categoryTemp.category!!
    }

    @Test
    fun addCategory_thenStatusShouldBeOk() {
        client.post()
                .uri("/categories/${category}")
                .exchange()
                .expectStatus().isOk
                .expectBody().isEmpty
    }

    @Test
    fun deleteCategory_thenStatusShouldBeOk() {
        client.delete()
                .uri("/categories/1")
                .exchange()
                .expectStatus().isOk
                .expectBody().isEmpty
    }

    @Test
    fun getCategory1_thenStatusShouldBeOk() {
        client.get()
                .uri("/categories/$categoryId")
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .json("{\"id\":${categoryId},\"category\":$categoryToGet}")
    }

    @Test
    fun getCategories_thenStatusShouldBeOk() {
        client.get()
                .uri("/categories/")
                .exchange()
                .expectStatus().isOk
    }
}