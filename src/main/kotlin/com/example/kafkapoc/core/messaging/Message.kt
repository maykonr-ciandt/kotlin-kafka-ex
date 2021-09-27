package com.example.kafkapoc.core.messaging

import java.io.Serializable

class Message(
    val version: String,
    val operation: MessageOperation,
    val `object`: String
) : Serializable