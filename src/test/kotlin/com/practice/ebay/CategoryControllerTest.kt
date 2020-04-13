package com.practice.ebay

import com.google.gson.Gson
import com.practice.ebay.controller.CategoryController
import com.practice.ebay.models.Category
import com.practice.ebay.models.User
import com.practice.ebay.repositories.CategoryRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.test.StepVerifier

@SpringBootTest
class CategoryControllerTest {

    lateinit var client: WebTestClient
    var gson = Gson()
    @Autowired
    lateinit var controller: CategoryController
    @Autowired
    lateinit var categoryRepository : CategoryRepository

    @BeforeEach
    fun setup() {
        client = WebTestClient.bindToController(controller).build()
    }

    @Test
    fun addCategory_thenStatusShouldBeOk() {
        var categoryList = categoryRepository.findAll().buffer(Int.MAX_VALUE).blockFirst()!!
        client.post()
                .uri("/categories/${categoryList.last().category?.plus(1)}")
                .exchange()
                .expectStatus().isOk
                .expectBody().isEmpty

        categoryList.add(Category(categoryList.last().id?.plus(1),categoryList.last().category?.plus(1)))
        StepVerifier.create(categoryRepository.findAll().log())
                .expectNextSequence(categoryList)
                .verifyComplete()
    }

    @Test
    fun deleteCategory_thenStatusShouldBeOk() {
        var categoryList = categoryRepository.findAll().buffer(Int.MAX_VALUE).blockFirst()!!
        client.delete()
                .uri("/categories/${categoryList.get(0).id}")
                .exchange()
                .expectStatus().isOk
                .expectBody().isEmpty

        categoryList.removeAt(0)
        StepVerifier.create(categoryRepository.findAll().log())
                .expectNextSequence(categoryList)
                .verifyComplete()
    }

    @Test
    fun getCategory1_thenStatusShouldBeOk() {
        var category = categoryRepository.findAll().log().blockLast()
        client.get()
                .uri("/categories/${category?.id}")
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .json(gson.toJson(category))
    }

    @Test
    fun getCategories_thenStatusShouldBeOk() {
        var categoryList = categoryRepository.findAll().buffer(Int.MAX_VALUE).blockFirst()!!
        client.get()
                .uri("/categories/")
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .json(gson.toJson(categoryList))
    }
}