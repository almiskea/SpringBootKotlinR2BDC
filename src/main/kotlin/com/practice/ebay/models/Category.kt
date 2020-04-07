package com.practice.ebay.models

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("pre_approved_categories")
data class Category(@Id var category:Int)