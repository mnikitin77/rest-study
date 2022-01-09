package com.mvnikitin.reststudy.integration

import com.mvnikitin.reststudy.model.ErrorDto
import com.mvnikitin.reststudy.utils.cow
import com.mvnikitin.reststudy.utils.headers
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

class AnimalsApiErrorHandlingTest(): ApiTestBase() {

    @Test
    fun `when POST request with incorrect JSON then 400`() {
        data class incorrectObject(
            val fieldOne: Int = 1,
            val fieldTwo: String? = null
        )
        val request = HttpEntity(incorrectObject(), headers())
        val response = restTemplate.exchange(
            url(),
            HttpMethod.POST,
            request,
            ErrorDto::class.java,
        )
        assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
        val actual = response.body
        assertThat(actual?.code).isEqualTo(HttpStatus.BAD_REQUEST.value())
    }

    @Test
    fun `when POST request with minimum id constraint vionation then 400`() {
        val expected = cow.copy(id = 0)
        val request = HttpEntity(expected, headers())
        val response = restTemplate.exchange(
            url(),
            HttpMethod.POST,
            request,
            ErrorDto::class.java,
        )
        assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
        val actual = response.body
        assertThat(actual?.code).isEqualTo(HttpStatus.BAD_REQUEST.value())
    }

    @Test
    fun `when POST request with minimum year constraint vionation then 400`() {
        val expected = cow.copy(birthYear = 1095)
        val request = HttpEntity(expected, headers())
        val response = restTemplate.exchange(
            url(),
            HttpMethod.POST,
            request,
            ErrorDto::class.java,
        )
        assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
        val actual = response.body
        assertThat(actual?.code).isEqualTo(HttpStatus.BAD_REQUEST.value())
    }

    @Test
    fun `when POST request with requestId not provided in headers then 400`() {
        val headers = HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)

        val request = HttpEntity(cow, headers)
        val response = restTemplate.exchange(
            url(),
            HttpMethod.POST,
            request,
            ErrorDto::class.java,
        )
        assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
        val actual = response.body
        assertThat(actual?.code).isEqualTo(HttpStatus.BAD_REQUEST.value())
    }

    @Test
    fun `when GET request with incorrect ID sent then 404`() {

        val response = restTemplate.exchange(
            "${url()}/100500",
            HttpMethod.GET,
            HttpEntity(null, headers()),
           ErrorDto::class.java,
        )
        assertThat(response.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
        val actual = response.body
        assertThat(actual?.code).isEqualTo(HttpStatus.NOT_FOUND.value())
    }

    @Test
    fun `when DELETE request with incorrect ID sent then 404`() {

        val response = restTemplate.exchange(
            "${url()}/100500",
            HttpMethod.DELETE,
            HttpEntity(null, headers()),
            ErrorDto::class.java,
        )
        assertThat(response.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }
}