package com.emint.kafka.consumer

import com.emint.model.KsqlReceivedMessage
import com.emint.service.NextStepRouterService
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import java.util.function.Consumer

@Configuration
class KsqlConsumerConfig(
    private val nextStepRouterService: NextStepRouterService
) {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @Bean
    fun ksqlConsumer(): Consumer<Message<KsqlReceivedMessage>> {
        return Consumer<Message<KsqlReceivedMessage>> { message ->
            try {
                val stepId = message.payload.stepId
                log.info("Received message from KSQL for stepId: $stepId")
                nextStepRouterService.processNextCondition(stepId)
            } catch (e: Exception) {
                log.error("Error processing KSQL message: ${e.message}", e)
                throw e
            }
        }
    }
}
