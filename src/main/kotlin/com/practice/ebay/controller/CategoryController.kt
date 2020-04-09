package com.practice.ebay.controller

import com.practice.ebay.exceptions.CustomExceptions
import com.practice.ebay.exceptions.NotEligibleForShippingException
import com.practice.ebay.models.Category
import com.practice.ebay.repositories.CategoryRepository
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/categories")
class CategoryController(val categoryRepository: CategoryRepository) {

    @GetMapping("/")
    suspend fun findAll(): MutableList<Category>? = categoryRepository.findAll().buffer(Int.MAX_VALUE).awaitFirstOrElse { mutableListOf()}

    @GetMapping("/{categoryId}")
    suspend fun findById(@PathVariable categoryId:Int): Category? = categoryRepository.findById(categoryId).awaitFirstOrNull()

    @DeleteMapping("/{categoryId}")
    suspend fun deleteById(@PathVariable categoryId:Int): Void? = categoryRepository.deleteById(categoryId).awaitFirstOrNull()

    @PutMapping("/{categoryId}/{category}")
    suspend fun save(@PathVariable categoryId:Int, @PathVariable category:Int): Category? = categoryRepository.save(Category(categoryId,category)).awaitFirstOrNull()

    @PostMapping("/{category}")
    suspend fun insertCategory(@PathVariable category:Int): Void? = categoryRepository.insertCategory(category)?.awaitFirstOrNull()?:
    throw CustomExceptions("This Category already exists")

    @DeleteMapping("/")
    suspend fun deleteAll(): Void? = categoryRepository.deleteAll().awaitFirstOrNull()
}