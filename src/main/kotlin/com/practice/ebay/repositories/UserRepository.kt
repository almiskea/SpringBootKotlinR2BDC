package com.practice.ebay.repositories

import com.practice.ebay.models.User
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: ReactiveCrudRepository<User, String>