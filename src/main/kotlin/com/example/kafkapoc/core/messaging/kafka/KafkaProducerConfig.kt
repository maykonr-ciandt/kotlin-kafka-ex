package com.example.kafkapoc.core.messaging.kafka

import com.example.kafkapoc.core.messaging.Message
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer

@Configuration
class KafkaProducerConfig(@Value("\${spring.kafka.bootstrap-servers}") val bootstrapAddress: String) {

    @Bean
    fun kafkaProducerFactory(): ProducerFactory<String?, Message?> {
        val configs: MutableMap<String?, Any?> = HashMap()
        configs[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java
        configs[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        configs[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        return DefaultKafkaProducerFactory(configs)
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String?, Message?> {
        return KafkaTemplate(kafkaProducerFactory())
    }
}