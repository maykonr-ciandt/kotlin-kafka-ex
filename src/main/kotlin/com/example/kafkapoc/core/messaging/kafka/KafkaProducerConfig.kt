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

/**
 * Producer for {@link ProducerFactory}. When Autowired or injected as a component,
 * it will produce the factory using this bean.
 *
 * @author Maykon Rissi
 * @since 2021-09-27
 * @param bootstrapAddress the address to the Kafka cluster
 */
@Configuration
class KafkaProducerConfig(@Value("\${spring.kafka.bootstrap-servers}") val bootstrapAddress: String) {

    /**
     * @return ProducerFactory<String?, Message?> with custom configurations
     */
    @Bean
    fun kafkaProducerFactory(): ProducerFactory<String?, Message?> {
        val configs = mutableMapOf<String, Any>()
        // Serializer for the message body
        configs[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java
        // Serializer for the message key
        configs[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        // Address to Kafka Cluster
        configs[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        return DefaultKafkaProducerFactory(configs)
    }

    /**
     * @return KafkaTemplate<CString?, Message?> with custom configurations
     */
    @Bean
    fun kafkaTemplate(): KafkaTemplate<String?, Message?> {
        return KafkaTemplate(kafkaProducerFactory())
    }
}