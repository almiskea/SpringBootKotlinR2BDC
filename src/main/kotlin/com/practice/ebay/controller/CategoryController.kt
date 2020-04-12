package com.practice.ebay.controller

import com.practice.ebay.exceptions.NotFoundException
import com.practice.ebay.exceptions.ResourceAlreadyExists
import com.practice.ebay.models.Category
import com.practice.ebay.repositories.CategoryRepository
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/categories")
class CategoryController(val categoryRepository: CategoryRepository) {

    @GetMapping("/")
    suspend fun findAll(): MutableList<Category>? = categoryRepository.findAll().buffer(Int.MAX_VALUE).awaitFirstOrElse { mutableListOf()}

    @GetMapping("/{categoryId}")
    suspend fun findById(@PathVariable categoryId:Int): Category? = categoryRepository.findById(categoryId).awaitFirstOrNull()?:
    throw NotFoundException("Id is not associated with any category")

    @DeleteMapping("/{categoryId}")
    suspend fun deleteById(@PathVariable categoryId:Int): Void? = categoryRepository.deleteById(categoryId).awaitFirstOrNull()

    @PutMapping("/{categoryId}/{category}")
    suspend fun save(@PathVariable categoryId:Int, @PathVariable category:Int): Category? = categoryRepository.save(Category(categoryId,category)).awaitFirstOrNull()?:
    throw NotFoundException("Id is not associated with any category")

    @PostMapping("/{category}")
    suspend fun insertCategory(@PathVariable category:Int): Void? = categoryRepository.insertCategory(category)?.onErrorMap { throw ResourceAlreadyExists("Category Already Exists") }?.awaitFirstOrNull()

    @DeleteMapping("/")
    suspend fun deleteAll(): Void? = categoryRepository.deleteAll().awaitFirstOrNull()
}