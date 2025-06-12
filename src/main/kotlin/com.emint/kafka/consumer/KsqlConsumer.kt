package com.emint.producer

import com.emint.model.KsqlReceivedMessage
import com.emint.service.NextStepRouterService
import org.slf4j.LoggerFactory

class KsqlConsumer(
    private val nextStepRouterService: NextStepRouterService
) {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    fun receivedFeedFromKsql(messageReceived: KsqlReceivedMessage){
        nextStepRouterService.processReceivedMessageForRouting(messageReceived)
    }
}
