package com.example

import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client

interface TestOps{

    @Get("/test")
    suspend fun test(): TestResponse

    data class TestResponse(val hello: String)
}

@Client("test-client")
interface TestClient : TestOps{
}
