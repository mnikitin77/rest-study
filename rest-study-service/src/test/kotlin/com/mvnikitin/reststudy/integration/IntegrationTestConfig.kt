package com.mvnikitin.reststudy.integration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class IntegrationTestConfig {

    @Bean
    fun restTemplate() = RestTemplate()
}