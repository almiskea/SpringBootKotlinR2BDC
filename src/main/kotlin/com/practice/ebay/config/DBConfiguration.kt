package com.practice.ebay.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.core.DatabaseClient

@Configuration
class DBConfiguration(db: DatabaseClient) {
    init {
        val initDb = db.execute {
            """ CREATE TABLE IF NOT EXISTS enrolled_users (
                    username VARCHAR(250) NOT NULL PRIMARY KEY
                );
                CREATE TABLE IF NOT EXISTS pre_approved_categories(
                    category LONG NOT NULL PRIMARY KEY
                );
                INSERT INTO enrolled_users (username) VALUES
                  ('Dan'),
                  ('Jen'),
                  ('Bella');
                INSERT INTO pre_approved_categories (category) VALUES
                  (1),
                  (2),
                  (3),
                  (4),
                  (5),
                  (6),
                  (7),
                  (8),
                  (9),
                  (10),
                  (11);
            """
        }
        initDb.then().subscribe()
    }
}