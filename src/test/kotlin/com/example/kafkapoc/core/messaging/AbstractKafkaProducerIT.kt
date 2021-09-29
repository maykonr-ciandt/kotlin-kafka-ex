package com.example.kafkapoc.core.messaging

import com.example.kafkapoc.core.messaging.producer.Producer
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.test.EmbeddedKafkaBroker
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.kafka.test.utils.KafkaTestUtils

@SpringBootTest
@EmbeddedKafka(
    partitions = 1,
    brokerProperties = ["listeners=PLAINTEXT://localhost:9092", "port=9092"]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class AbstractKafkaProducerIT {

    protected var producer: Producer? = null
    protected var embeddedKafkaBroker: EmbeddedKafkaBroker? = null
    protected var consumer: Consumer<Any?, Any?>? = null

    protected abstract fun getTopicName(): String

    protected fun doCreateConsumer() {
        val consumerProps = KafkaTestUtils.consumerProps("groupTest", "true", this.embeddedKafkaBroker)
        consumerProps[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"
        consumerProps[ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG] = "true"
        val consumer = DefaultKafkaConsumerFactory<Any?, Any?>(consumerProps).createConsumer()
        consumer.subscribe(listOf(getTopicName()))
        this.consumer = consumer
    }

    protected fun doCleanConsumer() {
        this.consumer = null
    }

    protected fun isHeaderPresent(messageRecord: ConsumerRecord<Any, Any>,
                                  headerKey: Any,
                                  headerValue: Any) : Boolean{
        return messageRecord.headers()
            .filter { header -> headerKey == header.key() }
            .map { header -> String(header.value()) }
            .any { value -> headerValue == value }
    }
}