package com.example.kafkapoc.core.messaging

import com.example.kafkapoc.domain.BaseEntity
import com.example.kafkapoc.domain.location.Location
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class MessageListener(val mapper: ObjectMapper) {

    @KafkaListener(
        topics = ["kafkapoc_locations"],
        groupId = "locations_group",
        id = "locations_1"
    )
    fun consume1(message: Message) {
        val typeReference = object : TypeReference<Location>() {}
        val location = this.mapper.readValue(message.`object`, typeReference)
        println("Consumer 1 : ${location.id}");
    }

    @KafkaListener(
        topics = ["kafkapoc_locations"],
        groupId = "locations_group",
        id = "locations_2"
    )
    fun consume2(message: Message) {
        val typeReference = object : TypeReference<Location>() {}
        val location = this.mapper.readValue(message.`object`, typeReference)
        println("Consumer 2 : ${location.id}");
    }
}