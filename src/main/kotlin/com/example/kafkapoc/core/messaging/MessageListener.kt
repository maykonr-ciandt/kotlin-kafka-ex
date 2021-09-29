package com.example.kafkapoc.core.messaging

import com.example.kafkapoc.domain.location.Location
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

/**
 * If all consumers use the same group id, messages in a topic are distributed among those consumers.
 * In other words, each consumer will get a non-overlapping subset of the messages. Having more
 * consumers in the same group increases the degree of parallelism and the overall throughput of
 * consumption. On the other hand, if each consumer is in its own group, each consumer will get
 * a full copy of all messages.
 *
 * @author Maykon Rissi
 * @since 2021-09-27
 * @param mapper to serialize the object into a json string
 */
@Component
class MessageListener(val mapper: ObjectMapper) {

    @KafkaListener(
        // Topics being listened
        topics = ["kafkapoc_locations"],
        // Consumer group id
        // consumer id
        id = "locations_1"
    )
    fun consume1(message: Message) {
        val typeReference = object : TypeReference<Location>() {}
        val location = this.mapper.readValue(message.`object`, typeReference)
        println("Consumer 1 : ${location.id}");
    }

    @KafkaListener(
        topics = ["kafkapoc_locations"],
        id = "locations_2"
    )
    fun consume2(message: Message) {
        val typeReference = object : TypeReference<Location>() {}
        val location = this.mapper.readValue(message.`object`, typeReference)
        println("Consumer 2 : ${location.id}");
    }

    @KafkaListener(
        topics = ["kafkapoc_locations"],
        id = "locations_3"
    )
    fun consume3(message: Message) {
        val typeReference = object : TypeReference<Location>() {}
        val location = this.mapper.readValue(message.`object`, typeReference)
        println("Consumer 3 : ${location.id}");
    }
}