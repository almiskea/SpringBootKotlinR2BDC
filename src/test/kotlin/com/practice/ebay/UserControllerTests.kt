package com.practice.ebay

import com.practice.ebay.controller.UserController
import com.practice.ebay.repositories.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
class UserControllerTests {

    lateinit var client: WebTestClient
    @Autowired
    lateinit var controller: UserController
    @Autowired
    lateinit var userRepository : UserRepository
    var user = "Tester"
    var userToGet = ""
    var userId = 1

    @BeforeEach
    fun setup() {
        client = WebTestClient.bindToController(controller).build()
        var userTemp = userRepository.findAll().blockLast()
        userId = userTemp?.id!!
        userToGet = userTemp.user!!
    }

    @Test
    fun addUser_thenStatusShouldBeOk() {
        client.post()
                .uri("/users/${user}")
                .exchange()
                .expectStatus().isOk
                .expectBody().isEmpty
    }

    @Test
    fun deleteUsers_thenStatusShouldBeOk() {
        client.delete()
                .uri("/users/1")
                .exchange()
                .expectStatus().isOk
                .expectBody().isEmpty
    }

    @Test
    fun getUser1_thenStatusShouldBeOk() {
        client.get()
                .uri("/users/$userId")
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .json("{\"id\":$userId,\"user\":\"$userToGet\"}")
    }

    @Test
    fun getUsers_thenStatusShouldBeOk() {
        client.get()
                .uri("/users/")
                .exchange()
                .expectStatus().isOk
    }
}