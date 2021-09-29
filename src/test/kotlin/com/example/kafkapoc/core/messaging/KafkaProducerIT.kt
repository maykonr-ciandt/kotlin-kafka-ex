package com.example.kafkapoc.core.messaging

import com.example.kafkapoc.core.messaging.kafka.KafkaProducer
import com.example.kafkapoc.core.messaging.kafka.KafkaQualifier
import com.example.kafkapoc.core.messaging.producer.Producer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.test.EmbeddedKafkaBroker
import org.springframework.kafka.test.utils.KafkaTestUtils


class KafkaProducerIT() : AbstractKafkaProducerIT() {

    @BeforeAll
    fun beforeAll(
        @Autowired @KafkaQualifier producer: Producer,
        @Autowired embeddedKafkaBroker: EmbeddedKafkaBroker?
    ) {
        super.producer = producer
        super.embeddedKafkaBroker = embeddedKafkaBroker
        super.doCreateConsumer()
    }

    override fun getTopicName(): String {
        return "topic1"
    }

    @Test
    fun `Should produce a message with serialized json object`() {
        // Given
        val message = Message(
            "v1", MessageOperation.DELETE, "my-test-value"
        )

        // Execute
        super.producer?.sendMessage(
            getTopicName(),
            message
        )

        // Test
        val singleRecord = KafkaTestUtils.getSingleRecord<Any, Any>(super.consumer, getTopicName())
        assertThat(singleRecord).isNotNull
        assertThat(super.isHeaderPresent(singleRecord, "version", "v1")).isEqualTo(true)
        assertThat(super.isHeaderPresent(singleRecord, "origin", "com.example.kafkapoc.core.messaging.kafka.KafkaProducer")).isEqualTo(true)
        assertThat(singleRecord.value()).isEqualTo("{\"version\":\"v1\",\"operation\":\"DELETE\",\"object\":\"my-test-value\"}")
    }


}