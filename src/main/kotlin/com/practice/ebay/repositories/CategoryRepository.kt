package com.practice.ebay.repositories

import com.practice.ebay.models.Category
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository: ReactiveCrudRepository<Category, Long>