package com.practice.ebay.repositories

import com.practice.ebay.config.CATEGORY_FIELD
import com.practice.ebay.config.ENROLLED_USERS_TABLE
import com.practice.ebay.config.PRE_APPROVED_CATEGORIES_TABLE
import com.practice.ebay.config.USERS_FIELD
import com.practice.ebay.models.Category
import com.practice.ebay.models.User
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface CategoryRepository: ReactiveCrudRepository<Category, Int>{

    @Modifying
    @Query("insert into $PRE_APPROVED_CATEGORIES_TABLE ($CATEGORY_FIELD) values (:category)")
    fun insertCategory(@Param("category") category: Int): Mono<Void?>?
}