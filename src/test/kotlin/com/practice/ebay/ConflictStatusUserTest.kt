package com.practice.ebay

import com.google.gson.Gson
import com.practice.ebay.controller.CategoryController
import com.practice.ebay.controller.UserController
import com.practice.ebay.models.User
import com.practice.ebay.repositories.CategoryRepository
import com.practice.ebay.repositories.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.test.StepVerifier

@SpringBootTest
class ConflictStatusUserTest {

    lateinit var client: WebTestClient
    @Autowired
    lateinit var userController: UserController
    @Autowired
    lateinit var userRepository : UserRepository

    @BeforeEach
    fun setup() {

    }

    @Test
    fun addUser_thenStatusShouldBeConflict() {
        client = WebTestClient.bindToController(userController).build()
        var userList = userRepository.findAll().buffer(Int.MAX_VALUE).blockFirst()!!
        client.post()
                .uri("/users/${userList.last().user}")
                .exchange()
                .expectStatus().is4xxClientError
    }

}