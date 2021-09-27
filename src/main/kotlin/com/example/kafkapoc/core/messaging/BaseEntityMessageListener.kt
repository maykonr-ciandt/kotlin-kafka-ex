package com.example.kafkapoc.core.messaging

import com.example.kafkapoc.core.messaging.kafka.KafkaQualifier
import com.example.kafkapoc.domain.BaseEntity
import org.springframework.stereotype.Component
import javax.persistence.PostPersist
import javax.persistence.PostRemove
import javax.persistence.PostUpdate

@Component
@Suppress("JpaEntityListenerInspection")
class BaseEntityMessageListener(@KafkaQualifier val producer: Producer) {

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
            val message = Message("v1", messageOperation, baseEntity)
            val topicName = "kafkapoc_" + baseEntity.javaClass.simpleName.lowercase() + "s"
            producer.sendMessage(topicName, message)
        }
    }
}