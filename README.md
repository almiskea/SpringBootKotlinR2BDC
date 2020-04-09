# Kotlin Spring Boot Kotlin Coroutine RestApi

### Reference Documentation
For further reference, please consider the following sections:

* [Acessing data with R2DBC](https://spring.io/guides/gs/accessing-data-r2dbc/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)

I have used this url extensively 
(https://spring.io/blog/2019/04/12/going-reactive-with-spring-coroutines-and-kotlin-flow)

## Documentation

- I have used this assignment as an opportunity to have fun, so I learned Kotlin and built the project 
- Exploring Kotlin, I found that coroutines are awesome, so I used them
- I wanted to build a powerful robust application that is using Reactive programming, so experimented with release candidates for, H2 reactive database platform using Spring R2DBC, and Kotlin Coroutines
- The trick was in making the rules flexibly easy to modify, by eaither adding rules. or modifying the existing rules.
- I have made the enrolled users and the categories; two database tables, so they will be easy to modify, by adding new enrolled users, categories, or removing them through either direct access to APIs or through the Rest Api interface I have created for them.
- Then for the minimum price, if the business don't think it will rarely modified then we might add it in the property file, but in a real scenario, it should be added in a caching server and cached in the service for at least 24 hours
- If the shipping program needs to high flexibility for modification, then the users and categories might get cached for a whole hour, to reduce access to the database, or maybe keep a cache that get updated automatically every half an hour or so  
- in the service, the main controller that clients would get access to is in the Validation Controller, one end point that they pass the parameters to and get the answer if the item is valid for the shipping program or not.
- The other endpoints are for the admins; to have a control over the whole data, from the enroll users to the minimum price for qualification.

Functionality
- For clients:-
    - /validation/valid endpoint to that accepts requestsParameters seller: String, title: String, price: Double and category: Int
    - it return a JSON {"valid":true} OR a 404 message exception that the item is not eligible for the shipping program 
- For Admins
    - User controller with the ability to add,modify,get or delete users
    - Category controller with the ability to add,modify,get or delete categories
    - MinimumPrice Controller with the ability to get or modify the minimum price

#### how to run the project

### Injellij
- import it to intellij and run it

### Commandline
- install gradle and java
- run ```./gradlew clean build``` to run the tests
- run ```./gradlew clean build & java -jar build/libs/ebay-1.0.1.jar``` to run the application with the tests
- run ```./graldew bootrun``` to start the application without the tests
- The application runs on port 8080 by default

#### Assumptions I have made

- I was not sure if the minimum price for the shipping program was a standered with all categories, or if there was a minimum price per category. I picked the first, which is a global universal pricing
- I assumed that I don't have to use a separate Database or use caching server implementation and I am able to use in memory database, for testing and demonstrating the concept
- I assumed that I don't need to implement a full integration testing, but unit testing lightly are sufficient 