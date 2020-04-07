package com.practice.ebay.repositories

import com.practice.ebay.models.Username
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UsernameRepository: ReactiveCrudRepository<Username, String>