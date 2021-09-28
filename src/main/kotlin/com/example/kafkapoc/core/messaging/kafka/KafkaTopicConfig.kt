package com.example.kafkapoc.core.messaging.kafka

import com.example.kafkapoc.core.messaging.Topics
import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaAdmin

/**
 * Create topics on Kafka Cluster
 *
 * @author Maykon Rissi
 * @since 2021-09-27
 * @param bootstrapAddress the address to the Kafka cluster
 */
@Configuration
class KafkaTopicConfig(@Value("\${spring.kafka.bootstrap-servers}") private val bootstrapAddress: String) {

    @Bean
    fun kafkaAdmin(): KafkaAdmin {
        val configs = HashMap<String, Any>()
        configs[AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        return KafkaAdmin(configs)
    }

    @Bean
    fun roadTopic(): NewTopic {
        return TopicBuilder
            .name(Topics.ROAD_TOPIC)
            // the number of copies of data over multiple brokers. The replication factor value should be
            // greater than 1 always (between 2 or 3). This helps to store a replica of the data in another
            // broker from where the user can access it.
            .replicas(3)
            // partitions allow a single topic to be distributed over multiple servers. That way it is
            // possible to store more data in a topic than what a single server could hold. If you
            // imagine you needed to store 10TB of data in a topic and you have 3 brokers, one option
            // would be to create a topic with one partition and store all 10TB on one broker. Another
            // option would be to create a topic with 3 partitions and spread 10 TB of data over all
            // the brokers.
            .partitions(3)
            .build()
    }

    @Bean
    fun locationTopic(): NewTopic {
        return TopicBuilder
            .name(Topics.LOCATION_TOPIC)
            .replicas(3)
            .partitions(3)
            .build()
    }
}