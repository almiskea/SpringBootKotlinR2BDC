package com.practice.ebay

import com.google.gson.Gson
import com.practice.ebay.controller.UserController
import com.practice.ebay.models.User
import com.practice.ebay.repositories.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.test.StepVerifier

@SpringBootTest
class UserControllerTests {

    lateinit var client: WebTestClient
    var gson = Gson()
    @Autowired
    lateinit var controller: UserController
    @Autowired
    lateinit var userRepository : UserRepository
    var user = "Tester"
    var userId = 1

    @BeforeEach
    fun setup() {
        client = WebTestClient.bindToController(controller).build()
        userId = userRepository.findAll().log().blockLast()?.id!!
    }

    @Test
    fun addUser_thenStatusShouldBeOk() {
        var userList = userRepository.findAll().buffer(Int.MAX_VALUE).blockFirst()!!
        StepVerifier.create(userRepository.findAll().log())
                .expectNextSequence(userList)
                .verifyComplete()

        client.post()
                .uri("/users/$user")
                .exchange()
                .expectStatus().isOk
                .expectBody().isEmpty

        userList.add(User(userList.last().id?.plus(1),"$user"))
        StepVerifier.create(userRepository.findAll().log())
                .expectNextSequence(userList)
                .verifyComplete()
    }

    @Test
    fun deleteUsers_thenStatusShouldBeOk() {
        var userList = userRepository.findAll().buffer(Int.MAX_VALUE).blockFirst()!!
        StepVerifier.create(userRepository.findAll().log())
                .expectNextSequence(userList)
                .verifyComplete()

        client.delete()
                .uri("/users/${userList.get(0).id}")
                .exchange()
                .expectStatus().isOk
                .expectBody().isEmpty

        userList.removeAt(0)
        StepVerifier.create(userRepository.findAll().log())
                .expectNextSequence(userList)
                .verifyComplete()
    }

    @Test
    fun getUser1_thenStatusShouldBeOk() {
        var user = userRepository.findById(userId).block()

        client.get()
                .uri("/users/$userId")
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .json(gson.toJson(user))
    }

    @Test
    fun getUsers_thenStatusShouldBeOk() {
        var userList = userRepository.findAll().buffer(Int.MAX_VALUE).blockFirst()!!
        client.get()
                .uri("/users/")
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .json(gson.toJson(userList))
    }
}