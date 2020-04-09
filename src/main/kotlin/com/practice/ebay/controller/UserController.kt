package com.practice.ebay.controller

import com.practice.ebay.exceptions.CustomExceptions
import com.practice.ebay.models.User
import com.practice.ebay.repositories.UserRepository
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(val userRepository: UserRepository) {

    // using buffer(Int.MAX_VALUE) is just for this small project, not for production
    @GetMapping("/")
    suspend fun findAll(): MutableList<User>? = userRepository.findAll().buffer(Int.MAX_VALUE).awaitFirstOrElse { mutableListOf() }

    @GetMapping("/{userId}")
    suspend fun findById(@PathVariable userId:Int): User? = userRepository.findById(userId).awaitFirstOrNull()

    @GetMapping("/{user}")
    suspend fun findByUser(@PathVariable user:String): User? = userRepository.findByUser(user)?.awaitFirstOrNull()

    @DeleteMapping("/{userId}")
    suspend fun deleteById(@PathVariable userId:Int): Void? = userRepository.deleteById(userId).awaitFirstOrNull()

    @PutMapping("/{userId}/{user}")
    suspend fun save(@PathVariable userId:Int,@PathVariable user:String): User? = userRepository.save(User(userId,user)).awaitFirstOrNull()

    @PostMapping("/{user}")
    suspend fun add(@PathVariable user:String): Void? = userRepository.insertUser(user)?.awaitFirstOrNull()

    @DeleteMapping("/")
    suspend fun deleteAll(): Void? = userRepository.deleteAll().awaitFirstOrNull()


}