package com.practice.ebay.repositories

import com.practice.ebay.config.ENROLLED_USERS_TABLE
import com.practice.ebay.config.USERS_FIELD
import com.practice.ebay.models.User
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Repository
interface UserRepository: ReactiveCrudRepository<User, Int>{

    @Query("select * from $ENROLLED_USERS_TABLE where $USERS_FIELD = :user")
    fun findByUser(@Param("user") user: String): Mono<User?>?

    @Query("SELECT " +
            "CASE WHEN EXISTS (select * from $ENROLLED_USERS_TABLE where $USERS_FIELD = :user) THEN true " +
            "ELSE false " +
            "END AS user " +
            "FROM $ENROLLED_USERS_TABLE;")
    fun findUserExists(@Param("user") user: String): Flux<Boolean?>?

    @Modifying
    @Query("insert into $ENROLLED_USERS_TABLE ($USERS_FIELD) values (:user)")
    fun insertUser(@Param("user") user: String?): Mono<Void?>?
}