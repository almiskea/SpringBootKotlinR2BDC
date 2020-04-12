package com.practice.ebay.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.core.DatabaseClient

@Configuration
class DBConfiguration(db: DatabaseClient, @Value("\${min.price.value}") val minPrice: Double) {
    init {
        System.setProperty("min.price.value", minPrice.toString())
        val initDb = db.execute {
            """ CREATE TABLE IF NOT EXISTS $ENROLLED_USERS_TABLE (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    $USERS_FIELD VARCHAR(250) NOT NULL UNIQUE
                );
                CREATE TABLE IF NOT EXISTS $PRE_APPROVED_CATEGORIES_TABLE(
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    $CATEGORY_FIELD INT NOT NULL UNIQUE
                );
                INSERT INTO $ENROLLED_USERS_TABLE ($USERS_FIELD) VALUES
                  ('Dan'),
                  ('Jen'),
                  ('Bella');
                INSERT INTO $PRE_APPROVED_CATEGORIES_TABLE ($CATEGORY_FIELD) VALUES
                  (1),
                  (2),
                  (3);
            """
        }
        initDb.then().subscribe()
    }
}