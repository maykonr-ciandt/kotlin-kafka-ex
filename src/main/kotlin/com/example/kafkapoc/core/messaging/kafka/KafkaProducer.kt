package com.example.kafkapoc.core.messaging.kafka

import com.example.kafkapoc.core.messaging.Message
import com.example.kafkapoc.core.messaging.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.header.internals.RecordHeader
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import org.springframework.util.concurrent.ListenableFutureCallback

/**
 * Concrete implementation for {@link Producer} using Kafka as a broker.
 *
 * @author Maykon Rissi
 * @since 2021-09-27
 * @param producer as the kafka template to be used to send messages
 */
@Component
@KafkaQualifier
class KafkaProducer constructor(val producer: KafkaTemplate<String?, Message?>) : Producer {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun sendMessage(topic: String, message: Message) {
        // Adding headers to allow kafka to filter information on subscribers
        val record: ProducerRecord<String?, Message?> = ProducerRecord<String?, Message?>(topic, message)
        record.headers().add(RecordHeader("version", message.version.encodeToByteArray()))
        record.headers().add(RecordHeader("origin", this.javaClass.canonicalName.toByteArray()))

        // Originally Kafka is an async tool. To allow a better debug and to show how it works
        // It will make a sync call.
        producer.send(record).addCallback(
            object : ListenableFutureCallback<SendResult<String?, Message?>?> {
                override fun onSuccess(stringMessageSendResult: SendResult<String?, Message?>?) {
                    logger.info(
                        "Message sent to topic {}. (Operation {} | Partition {} | Offset {})",
                        topic,
                        message.operation,
                        // Partition on Kafka to where the data will be sent to
                        stringMessageSendResult!!.recordMetadata.partition(),
                        // Message offset (number in line) for that partition
                        stringMessageSendResult.recordMetadata.offset()
                    )
                }

                override fun onFailure(throwable: Throwable) {
                    logger.error("unable to send message = {}", message, throwable)
                }
            })
    }
}