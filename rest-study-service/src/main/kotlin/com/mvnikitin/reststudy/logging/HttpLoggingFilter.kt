package com.mvnikitin.reststudy.logging

import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Component
import org.springframework.util.StreamUtils
import org.springframework.web.util.ContentCachingResponseWrapper
import java.nio.charset.Charset
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class HttpLoggingFilter(): Filter{

    override fun doFilter(
        servletRequest: ServletRequest,
        servletResponse: ServletResponse,
        filterChain: FilterChain
    ) {
        val request = MultiReadHttpServletRequest(servletRequest as HttpServletRequest)
        val requestId = request.getHeader("X-Request-ID")
        val params = request.parameterMap
        val requestBody = requestBody(request)
        log.info("Request ID=$requestId to ${request.requestURI} ${params.takeIf { it.isNotEmpty() }?.let { "\n\twith parapeters: ${it}" } ?: ""} ${requestBody.takeIf { it.isNotEmpty() }?.let { "\n\trequest body: ${it}" } ?: ""}")

        val response = ContentCachingResponseWrapper(servletResponse as HttpServletResponse)

        filterChain.doFilter(request, response)

        val responseBody = responseBody(response)

// !!! THIS CALL IS REQUIRED TO RESTORE THE BODY AFTER BEING READ !!!
        response.copyBodyToResponse()
        log.info("Request ID=$requestId resulted with code ${response.status} ${responseBody.takeIf { it.isNotEmpty() }?.let { "\n\tresponse body: ${it}" } ?: ""}")
    }

    private fun responseBody(response: ContentCachingResponseWrapper) =
        String(response.contentAsByteArray, Charset.forName(response.characterEncoding))

    private fun requestBody(request: HttpServletRequest): String {
        val body = StreamUtils.copyToByteArray(request.inputStream)
        return String(body, Charset.forName(request.characterEncoding))
    }

    companion object {
        private val log = LogManager.getLogger(HttpLoggingFilter::class.java)
    }
}