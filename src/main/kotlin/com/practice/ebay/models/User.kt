package com.practice.ebay.models

import com.practice.ebay.config.ENROLLED_USERS_TABLE
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("$ENROLLED_USERS_TABLE")
data class User(@Id var id: Int,var user:String)

