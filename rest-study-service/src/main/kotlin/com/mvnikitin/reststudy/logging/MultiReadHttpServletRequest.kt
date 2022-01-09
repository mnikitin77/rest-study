package com.mvnikitin.reststudy.logging

import org.springframework.util.StreamUtils
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper


class MultiReadHttpServletRequest(
    private val request: HttpServletRequest
) : HttpServletRequestWrapper(request) {

    private val body = StreamUtils.copyToByteArray(request.inputStream)

    override fun getInputStream() = MultiReadServletInputStream(body)

    override fun getReader() = BufferedReader(InputStreamReader(ByteArrayInputStream(body)))
}