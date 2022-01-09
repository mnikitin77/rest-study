package com.mvnikitin.reststudy.integration

import com.mvnikitin.reststudy.dao.AnimalRepository
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiTestBase {
    @LocalServerPort
    protected lateinit var port: String

    @Autowired
    protected lateinit var restTemplate: TestRestTemplate

    @Autowired
    protected lateinit var animalsRepository: AnimalRepository

    protected fun url() = "http://localhost:$port/api/v1/animals"

    @BeforeEach
    fun init() {
        animalsRepository.deleteAll()
    }
}