package com.mvnikitin.reststudy.logging

import java.io.ByteArrayInputStream
import javax.servlet.ReadListener
import javax.servlet.ServletInputStream

class MultiReadServletInputStream(
    private val data: ByteArray
) : ServletInputStream() {

    var stream = ByteArrayInputStream(data)

    override fun read() = stream.read()

    override fun isFinished() = stream.available() == 0

    override fun isReady() = true

    override fun setReadListener(listener: ReadListener) {}
}