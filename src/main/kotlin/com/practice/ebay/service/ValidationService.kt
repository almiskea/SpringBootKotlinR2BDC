package com.practice.ebay.service

import com.practice.ebay.exceptions.CustomExceptions
import org.springframework.stereotype.Service
import com.practice.ebay.models.Username
import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.r2dbc.core.*

@Service
class ValidationService(private val client: DatabaseClient, @Value("\${min.price.value}")
val minPrice :Double) {

    suspend fun validate(username: String,category :Int, passedPrice :Double): Username? =
            client.execute("SELECT username FROM enrolled_users WHERE username = :username " +
                    "and exists (select category from pre_approved_categories where category = :category) " +
                    "and :passedPrice >= :minPrice")
                    .bind("username", username)
                    .bind("category",category)
                    .bind("passedPrice",passedPrice)
                    .bind("minPrice",minPrice)
                    .asType<Username>()
                    .fetch()
                    .awaitOneOrNull()

}