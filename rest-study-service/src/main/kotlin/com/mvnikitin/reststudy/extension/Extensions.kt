package com.mvnikitin.reststudy.extension

import java.nio.charset.Charset
import javax.servlet.http.HttpServletRequest

fun ByteArray.toString() = String(this, Charset.forName("UTF-8"))

fun HttpServletRequest.requestId() = this.getHeader("X-Request-ID")