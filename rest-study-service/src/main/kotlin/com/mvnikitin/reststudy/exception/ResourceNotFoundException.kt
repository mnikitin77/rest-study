package com.mvnikitin.reststudy.exception

class ResourceNotFoundException: Exception {
    constructor(message: String): super(message)
    constructor(message: String, cause: Throwable): super(message, cause)
}