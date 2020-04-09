package com.practice.ebay.service

import com.practice.ebay.config.CATEGORY_FIELD
import com.practice.ebay.config.ENROLLED_USERS_TABLE
import com.practice.ebay.config.PRE_APPROVED_CATEGORIES_TABLE
import com.practice.ebay.config.USERS_FIELD
import org.springframework.stereotype.Service
import com.practice.ebay.models.User
import org.springframework.data.r2dbc.core.*

@Service
class ValidationService(private val client: DatabaseClient) {

    suspend fun validate(username: String, category:Int, passedPrice:Double, minPrice: Double?): User? =
            client.execute("select $USERS_FIELD from $ENROLLED_USERS_TABLE where $USERS_FIELD = :username " +
                    "and exists (select $CATEGORY_FIELD from $PRE_APPROVED_CATEGORIES_TABLE where $CATEGORY_FIELD = :category) " +
                    "and :passedPrice >= :minPrice")
                    .bind("username", username)
                    .bind("category",category)
                    .bind("passedPrice",passedPrice)
                    .bind("minPrice",minPrice)
                    .asType<User>()
                    .fetch()
                    .awaitOneOrNull()

}