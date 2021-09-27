package com.example.kafkapoc.domain

import com.example.kafkapoc.core.messaging.BaseEntityMessage
import com.example.kafkapoc.core.messaging.Message
import com.example.kafkapoc.core.messaging.MessageOperation
import com.example.kafkapoc.core.messaging.kafka.KafkaQualifier
import com.example.kafkapoc.core.messaging.producer.Producer
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import javax.persistence.PostPersist
import javax.persistence.PostRemove
import javax.persistence.PostUpdate

@Component
@Suppress("JpaEntityListenerInspection")
class BaseEntityListener(@KafkaQualifier val producer: Producer, val mapper: ObjectMapper) {

    @PostPersist
    private fun postPersist(baseEntity: BaseEntity) {
        produceMessage(baseEntity, MessageOperation.INSERT)
    }

    @PostUpdate
    private fun postUpdate(baseEntity: BaseEntity) {
        produceMessage(baseEntity, MessageOperation.UPDATE)
    }

    @PostRemove
    private fun postRemove(baseEntity: BaseEntity) {
        produceMessage(baseEntity, MessageOperation.DELETE)
    }

    private fun produceMessage(baseEntity: BaseEntity, messageOperation: MessageOperation) {
        if (baseEntity is BaseEntityMessage) {
            val message = Message("v1", messageOperation, mapper.writeValueAsString(baseEntity))
            val topicName = "kafkapoc_" + baseEntity.javaClass.simpleName.lowercase() + "s"
            producer.sendMessage(topicName, message)
        }
    }
}