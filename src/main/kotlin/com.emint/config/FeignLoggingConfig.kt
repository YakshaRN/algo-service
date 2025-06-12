package com.emint.config
import feign.Logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignLoggingConfig {
    @Bean
    fun feignLoggerLevel(): Logger.Level {
        return Logger.Level.FULL
    }

//    @Bean
//    fun feignLogger(): Logger {
//        return CustomFeignLogger()
//    }
//
//    @Bean
//    fun requestInterceptor(): RequestInterceptor {
//        return RequestInterceptor { requestTemplate: RequestTemplate ->
//            val requestId = MDC.get(MdcKeys.REQUEST_ID)
//            if (requestId != null) {
//                requestTemplate.header(EmintRequestHeaders.EMINT_REQUEST_ID, requestId)
//            }
//        }
//    }
}
