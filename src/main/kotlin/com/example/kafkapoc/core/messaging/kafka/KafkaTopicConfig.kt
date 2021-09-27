package com.example.kafkapoc.core.messaging.kafka

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaAdmin

@Configuration
class KafkaTopicConfig(@Value("\${spring.kafka.bootstrap-servers}") private val bootstrapAddress: String) {

    @Bean
    fun kafkaAdmin(): KafkaAdmin {
        val configs = HashMap<String, Any>()
        configs[AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        return KafkaAdmin(configs)
    }

    @Bean
    fun kafkaPocTruckTopic(): NewTopic {
        return TopicBuilder
            .name("kafkapoc_locations")
            .replicas(3)
            .partitions(3)
            .build()
    }

    @Bean
    fun kafkaPocLocationTopic(): NewTopic {
        return TopicBuilder
            .name("kafkapoc_roads")
            .replicas(3)
            .partitions(3)
            .build()
    }
}