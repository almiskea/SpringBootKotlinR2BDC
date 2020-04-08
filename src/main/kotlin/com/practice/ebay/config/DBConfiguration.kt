package com.practice.ebay.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.core.DatabaseClient

@Configuration
class DBConfiguration(db: DatabaseClient) {
    init {
        val initDb = db.execute {
            """ CREATE TABLE IF NOT EXISTS $ENROLLED_USERS_TABLE (
                    $USERS_FIELD VARCHAR(250) NOT NULL PRIMARY KEY
                );
                CREATE TABLE IF NOT EXISTS $PRE_APPROVED_CATEGORIES_TABLE(
                    $CATEGORY_FIELD LONG NOT NULL PRIMARY KEY
                );
                INSERT INTO $ENROLLED_USERS_TABLE ($USERS_FIELD) VALUES
                  ('Dan'),
                  ('Jen'),
                  ('Bella');
                INSERT INTO $PRE_APPROVED_CATEGORIES_TABLE ($CATEGORY_FIELD) VALUES
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