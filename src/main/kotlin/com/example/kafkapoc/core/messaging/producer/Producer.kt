package com.example.kafkapoc.core.messaging.producer

import com.example.kafkapoc.core.messaging.Message

interface Producer {
    fun sendMessage(topic: String, message: Message)
}