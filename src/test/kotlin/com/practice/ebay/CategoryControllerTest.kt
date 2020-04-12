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
    var category = 46
    var categoryId = 1

    @BeforeEach
    fun setup() {
        client = WebTestClient.bindToController(controller).build()
        categoryId = categoryRepository.findAll().log().blockLast()?.id!!
    }

    @Test
    fun addCategory_thenStatusShouldBeOk() {
        var categoryList = categoryRepository.findAll().buffer(Int.MAX_VALUE).blockFirst()!!
        StepVerifier.create(categoryRepository.findAll().log())
                .expectNextSequence(categoryList)
                .verifyComplete()

        client.post()
                .uri("/categories/${category}")
                .exchange()
                .expectStatus().isOk
                .expectBody().isEmpty

        categoryList.add(Category(categoryList.last().id?.plus(1),category))
        StepVerifier.create(categoryRepository.findAll().log())
                .expectNextSequence(categoryList)
                .verifyComplete()
    }

    @Test
    fun deleteCategory_thenStatusShouldBeOk() {
        var categoryList = categoryRepository.findAll().buffer(Int.MAX_VALUE).blockFirst()!!
        StepVerifier.create(categoryRepository.findAll().log())
                .expectNextSequence(categoryList)
                .verifyComplete()

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
        var category = categoryRepository.findById(categoryId).block()!!
        client.get()
                .uri("/categories/$categoryId")
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .json(gson.toJson(category))
    }

    @Test
    fun getCategories_thenStatusShouldBeOk() {
        var categoryList = categoryRepository.findAll().buffer(Int.MAX_VALUE).blockFirst()!!
        StepVerifier.create(categoryRepository.findAll().log())
                .expectNextSequence(categoryList)
                .verifyComplete()
        client.get()
                .uri("/categories/")
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .json(gson.toJson(categoryList))
    }
}