package com.emint.config

import com.emint.model.emint.Instruments
import com.emint.model.emint.MarketFeedDto
import com.emint.model.emint.OpenOrderCache
import com.emint.model.emint.PositionsInUiRedisDto
import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {

    @Bean(name = ["redisTemplateForInstrumentDetail"])
    fun redisTemplateForInstrumentDetail(factory: RedisConnectionFactory): RedisTemplate<String, Instruments> {
        val om = ObjectMapper()
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
        om.configure(DeserializationFeature.USE_LONG_FOR_INTS, true)
        val jackson2JsonRedisSerializer = Jackson2JsonRedisSerializer(Instruments::class.java)
        val stringRedisSerializer = StringRedisSerializer()
        val template = RedisTemplate<String, Instruments>()
        template.connectionFactory = factory
        template.keySerializer = stringRedisSerializer
        template.valueSerializer = stringRedisSerializer
        template.hashKeySerializer = stringRedisSerializer
        template.hashValueSerializer = jackson2JsonRedisSerializer
        template.afterPropertiesSet()
        return template
    }

    @Bean(name = ["redisTemplateForLtp"])
    fun redisTemplateForLtp(factory: RedisConnectionFactory): RedisTemplate<String, MarketFeedDto> {
        val om = ObjectMapper()
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
        om.configure(DeserializationFeature.USE_LONG_FOR_INTS, true)
        val jackson2JsonRedisSerializer = Jackson2JsonRedisSerializer(MarketFeedDto::class.java)
        val stringRedisSerializer = StringRedisSerializer()
        val template = RedisTemplate<String, MarketFeedDto>()
        template.connectionFactory = factory
        template.keySerializer = stringRedisSerializer
        template.valueSerializer = stringRedisSerializer
        template.hashKeySerializer = stringRedisSerializer
        template.hashValueSerializer = jackson2JsonRedisSerializer
        template.afterPropertiesSet()
        return template
    }

    @Bean(name = ["redisTemplateForOpenOrders"])
    fun redisTemplateForOpenOrders(factory: RedisConnectionFactory): RedisTemplate<String, OpenOrderCache> {
        val om = ObjectMapper()
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
        om.configure(DeserializationFeature.USE_LONG_FOR_INTS, true)
        val jackson2JsonRedisSerializer = Jackson2JsonRedisSerializer(OpenOrderCache::class.java)
        val stringRedisSerializer = StringRedisSerializer()
        val template = RedisTemplate<String, OpenOrderCache>()
        template.connectionFactory = factory
        template.keySerializer = stringRedisSerializer
        template.valueSerializer = stringRedisSerializer
        template.hashKeySerializer = stringRedisSerializer
        template.hashValueSerializer = jackson2JsonRedisSerializer
        template.afterPropertiesSet()
        return template
    }

    @Bean(name = ["redisTemplateForPositionsInUi"])
    fun redisTemplateForPositionsInUi(factory: RedisConnectionFactory): RedisTemplate<String, PositionsInUiRedisDto> {
        val jackson2JsonRedisSerializer = Jackson2JsonRedisSerializer(PositionsInUiRedisDto::class.java)
        val stringRedisSerializer = StringRedisSerializer()
        val template = RedisTemplate<String, PositionsInUiRedisDto>()
        template.connectionFactory = factory
        template.keySerializer = stringRedisSerializer
        template.valueSerializer = stringRedisSerializer
        template.hashKeySerializer = stringRedisSerializer
        template.hashValueSerializer = jackson2JsonRedisSerializer
        template.afterPropertiesSet()
        return template
    }
}
