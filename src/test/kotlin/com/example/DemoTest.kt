package com.example

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
class DemoTest {

    companion object {
        private var wireMockServer: WireMockServer? = null

        @BeforeAll
        @JvmStatic
        fun setUpAll() {
            wireMockServer = WireMockServer(WireMockConfiguration.wireMockConfig().port(5556))
            wireMockServer!!.start()
            WireMock.configureFor("localhost", 5556)
        }

        @AfterAll
        fun tearDown() {
            wireMockServer!!.stop()
        }
    }

    @Inject
    private lateinit var channelCustomizer: ChannelCustomizer

    @Inject
    private lateinit var client: TestClient

    @Test
    fun `should call channel customizer`(): Unit = runBlocking {
        WireMock.stubFor(
            WireMock.get(WireMock.urlPathEqualTo("/test"))
                .willReturn(
                    WireMock.okJson("""{"hello": "world"}""")
                )
        )

        val response = client.test()


        assertEquals("world", response.hello)
        assertTrue(channelCustomizer.clientChannelInvoked){"ChannelCustomizer wasn't called for client"}
    }
}
