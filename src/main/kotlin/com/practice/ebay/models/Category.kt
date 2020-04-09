package com.practice.ebay.models

import com.practice.ebay.config.PRE_APPROVED_CATEGORIES_TABLE
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("$PRE_APPROVED_CATEGORIES_TABLE")
data class Category(@Id var id:Int?, var category:Int?)