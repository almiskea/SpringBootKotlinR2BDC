package com.practice.ebay.models

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("enrolled_users")
data class Username(@Id var username:String)