package com.practice.ebay

import com.google.gson.Gson
import com.practice.ebay.controller.UserController
import com.practice.ebay.models.User
import com.practice.ebay.repositories.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
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


    @BeforeEach
    fun setup() {
        client = WebTestClient.bindToController(controller).build()
    }

    @Test
    fun addUser_thenStatusShouldBeOk() {
        var userList = userRepository.findAll().buffer(Int.MAX_VALUE).blockFirst()!!
        client.post()
                .uri("/users/${userList.last().user?.plus("Test")}")
                .exchange()
                .expectStatus().isOk
                .expectBody().isEmpty

        userList.add(User(userList.last().id?.plus(1),"${userList.last().user?.plus("Test")}"))
        StepVerifier.create(userRepository.findAll().log())
                .expectNextSequence(userList)
                .verifyComplete()
    }
//
//    @Test
//    @Order(2)
//    fun addUser_thenStatusShouldBeConflict() {
//        var userList = userRepository.findAll().buffer(Int.MAX_VALUE).blockFirst()!!
//        client.post()
//                .uri("/users/${userList.last().user}")
//                .exchange()
//                .expectStatus().is4xxClientError
////
////        StepVerifier.create(userRepository.findAll().log())
////                .expectNextSequence(userList)
////                .verifyComplete()
//    }

    @Test
    fun deleteUsers_thenStatusShouldBeOk() {
        var userList = userRepository.findAll().buffer(Int.MAX_VALUE).blockFirst()!!
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
        var user = userRepository.findAll().log().blockLast()
        client.get()
                .uri("/users/${user?.id}")
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