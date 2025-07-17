package com.emint.service.broker

import com.emint.data.broker.KiteApiConfig
import com.zerodhatech.kiteconnect.KiteConnect
import com.zerodhatech.models.User
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class KiteAuthService(
    val kiteConnect: KiteConnect, // Injects the session-scoped bean
    val kiteApiConfig: KiteApiConfig
) {
    companion object {
        val log = LoggerFactory.getLogger(this::class.java)
    }

    fun generateSession(requestToken: String): User {
        log.info("Attempting to generate session with request_token.")

        val kiteConnect = KiteConnect("//")

        val user = kiteConnect.generateSession(requestToken, kiteApiConfig.secret)

        log.info("Session data received for userId: {}", user.userId)

        kiteConnect.setUserId(user.userId)
        kiteConnect.setAccessToken(user.accessToken)
        kiteConnect.setPublicToken(user.publicToken)

        kiteConnect.setSessionExpiryHook {
            log.warn("Kite session expired for userId: {}", kiteConnect.getUserId())
        }

        return user
    }
}
