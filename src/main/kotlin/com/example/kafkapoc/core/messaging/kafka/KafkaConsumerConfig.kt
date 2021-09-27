package com.example.kafkapoc.core.messaging.kafka

import com.example.kafkapoc.core.messaging.Message
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import org.springframework.kafka.support.serializer.JsonDeserializer

@EnableKafka
@Configuration
class KafkaConsumerConfig(@Value("\${spring.kafka.bootstrap-servers}") val bootstrapAddress: String) {

    @Bean
    fun consumerFactory(): ConsumerFactory<String?, Message?> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        return DefaultKafkaConsumerFactory<String?, Message?>(
            props,
            StringDeserializer(),
            JsonDeserializer(Message::class.java)
        )
    }

    @Bean
    fun kafkaListenerContainerFactory(): KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String?, Message?>>? {
        val factory: ConcurrentKafkaListenerContainerFactory<String?, Message?> =
            ConcurrentKafkaListenerContainerFactory<String?, Message?>()
        factory.consumerFactory = consumerFactory()
        return factory
    }
}