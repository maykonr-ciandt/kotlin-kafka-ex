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
 * @see ProducerConfig for further documentation
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
        // "The number of acknowledgments the producer requires the leader to have received before considering a request complete.
        // This controls the durability of records that are sent. The following settings are allowed: <ul>
        // <li><code>acks=0</code> If set to zero then the producer will not wait for any acknowledgment from the
        // server at all. The record will be immediately added to the socket buffer and considered sent. No guarantee can be
        // made that the server has received the record in this case, and the <code>retries</code> configuration will not
        // take effect (as the client won't generally know of any failures). The offset given back for each record will
        // always be set to <code>-1</code>.
        // <li><code>acks=1</code> This will mean the leader will write the record to its local log but will respond
        // without awaiting full acknowledgement from all followers. In this case should the leader fail immediately after
        // acknowledging the record but before the followers have replicated it then the record will be lost.
        // <li><code>acks=all</code> This means the leader will wait for the full set of in-sync replicas to
        // acknowledge the record. This guarantees that the record will not be lost as long as at least one in-sync replica
        // remains alive. This is the strongest available guarantee. This is equivalent to the acks=-1 setting.
        // </ul>
        configs[ProducerConfig.ACKS_CONFIG] = "all"
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