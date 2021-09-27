package com.example.kafkapoc.core.messaging.kafka

import com.example.kafkapoc.core.messaging.Message
import com.example.kafkapoc.core.messaging.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.header.internals.RecordHeader
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import org.springframework.util.concurrent.ListenableFutureCallback

@Component
@KafkaQualifier
class KafkaProducer constructor(val producer: KafkaTemplate<String?, Message?>) : Producer {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun sendMessage(topic: String, message: Message) {
        val record: ProducerRecord<String?, Message?> = ProducerRecord<String?, Message?>(topic, message)
        record.headers().add(RecordHeader("version", message.version.encodeToByteArray()))
        record.headers().add(RecordHeader("origin", this.javaClass.canonicalName.toByteArray()))

        producer.send(record).addCallback(
            object : ListenableFutureCallback<SendResult<String?, Message?>?> {
                override fun onSuccess(stringMessageSendResult: SendResult<String?, Message?>?) {
                    logger.info(
                        "Message sent to topic {}. (Operation {} | Partition {} | Offset {})",
                        topic,
                        message.operation,
                        stringMessageSendResult!!.recordMetadata.partition(),
                        stringMessageSendResult.recordMetadata.offset()
                    )
                }

                override fun onFailure(throwable: Throwable) {
                    logger.error("unable to send message = {}", message, throwable)
                }
            })
    }
}