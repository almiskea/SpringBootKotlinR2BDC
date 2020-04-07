package com.practice.ebay

import com.practice.ebay.controller.ValidationController
import com.practice.ebay.repositories.CategoryRepository
import com.practice.ebay.repositories.UsernameRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
class EbayApplicationTests(@Value("\${min.price.value}")
						   val minPrice :Double) {

	lateinit var client: WebTestClient
	@Autowired
	lateinit var controller: ValidationController
	@Autowired
	lateinit var usernameRepository : UsernameRepository
	@Autowired
	lateinit var categoryRepository : CategoryRepository
	lateinit var seller: String
	var category : Int = 0
	var price = minPrice;
	var title = "Shipping Service"


	@BeforeEach
	fun setup() {
		client = WebTestClient.bindToController(controller).build()
		category = categoryRepository.findAll().blockLast()?.category ?: category
		seller = usernameRepository.findAll().blockLast()?.username ?: "Dan"
	}

	@Test
	fun testBaseEndPoint() {
		client.get()
				.uri("/")
				.exchange()
				.expectStatus().isOk
	}
	@Test
	fun testValidateEndpoint_thenFindExpectedJson() {
		client.get()
				.uri("/validate?seller=${seller}&category=${category}&title=${title}&price=${price}")
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.json("{\"eligible\":true}")
	}

	@Test
	fun testValidateEndpoint_thenStatusShouldBeOk() {
		client.get()
				.uri("/validate?seller=${seller}&category=${category}&title=${title}&price=${price}")
				.exchange()
				.expectStatus().isOk
	}

	@Test
	fun testValidateEndpoint_withWrongPrice() {
		client.get()
				.uri("/validate?seller=${seller}crapIsTheDeal&category=${category}&title=${title}&price=${price-10}")
				.exchange()
				.expectStatus().isNotFound
	}
	@Test
	fun testValidateEndpoint_withUnenrolledSeller() {
		client.get()
				.uri("/validate?seller=${seller}CrapThisIsReal&category=${category}&title=${title}&price=${price}")
				.exchange()
				.expectStatus().isNotFound
	}
	@Test
	fun testValidateEndpoint_withWrongCategory() {
		client.get()
				.uri("/validate?seller=${seller}&category=${category+100}&title=${title}&price=${price}")
				.exchange()
				.expectStatus().isNotFound
	}


}
