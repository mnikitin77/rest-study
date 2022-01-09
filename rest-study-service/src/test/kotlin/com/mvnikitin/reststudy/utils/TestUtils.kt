package com.mvnikitin.reststudy.utils

import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import java.util.*

fun headers(requestId: String = UUID.randomUUID().toString()): HttpHeaders {
    val headers = HttpHeaders()
    headers.setContentType(MediaType.APPLICATION_JSON)
    headers.add("X-Request-ID", requestId)
    return headers
}