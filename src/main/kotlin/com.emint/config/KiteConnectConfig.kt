package com.emint.config

import com.emint.data.broker.KiteApiConfig
import com.zerodhatech.kiteconnect.KiteConnect
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.web.context.WebApplicationContext

@Configuration
class KiteConnectConfig(private val kiteApiConfig: KiteApiConfig) {

    /**
     * Creates a session-scoped KiteConnect bean.
     * A new instance of KiteConnect is created for each user's HTTP session,
     * ensuring that user authentication data (accessToken, userId) is isolated
     * and not shared between different users. This is critical for a multi-user
     * application.
     *
     * The proxyMode ensures that this session-scoped bean can be injected into
     * singleton-scoped beans (like controllers and services) without issues.
     */
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    fun kiteConnect(): KiteConnect {
        // A new KiteConnect instance is created for each session.
        // It is initialized with the global API key.
        val kiteConnect = KiteConnect(kiteApiConfig.key)

        // The userId and tokens will be set on this session-specific instance
        // after the user completes the login flow.
        return kiteConnect
    }
}
