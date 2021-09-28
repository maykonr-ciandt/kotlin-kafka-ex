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

/**
 * Producer for {@link ConsumerFactory}. When Autowired or injected as a component,
 * it will produce the factory using this bean.
 *
 * @author Maykon Rissi
 * @since 2021-09-27
 * @param bootstrapAddress the address to the Kafka cluster
 * @see EnableKafka is a must-have annotations to allow the service to consume messages
 */
@EnableKafka
@Configuration
class KafkaConsumerConfig(@Value("\${spring.kafka.bootstrap-servers}") val bootstrapAddress: String) {

    /**
     * @return ConsumerFactory<String?, Message?> with custom configurations
     */
    @Bean
    fun consumerFactory(): ConsumerFactory<String?, Message?> {
        val props: MutableMap<String, Any> = HashMap()
        // If the broker lose the index, due to a manual removal for example,
        // it will use this config to know from where to start
        props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest";

        props[ConsumerConfig.GROUP_ID_CONFIG] = "kafka_poc";
        // Address to Kafka Cluster
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        return DefaultKafkaConsumerFactory<String?, Message?>(
            props,
            StringDeserializer(),
            JsonDeserializer(Message::class.java)
        )
    }

    /**
     * @return KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String?, Message?>>? with custom configurations
     */
    @Bean
    fun kafkaListenerContainerFactory(): KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String?, Message?>>? {
        val factory: ConcurrentKafkaListenerContainerFactory<String?, Message?> =
            ConcurrentKafkaListenerContainerFactory<String?, Message?>()
        // Producing the bean with the custom factory
        factory.consumerFactory = consumerFactory()
        return factory
    }
}