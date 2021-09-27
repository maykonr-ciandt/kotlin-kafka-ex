package com.example.kafkapoc.core.messaging

interface Producer {
    fun sendMessage(topic: String, message: Message)
}