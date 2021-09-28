package com.example.kafkapoc.core.messaging

import java.io.Serializable

/**
 * @author Maykon Rissi
 * @since 2021-09-27
 */
class Message(
    // Message version. With this prop we can have consumers for each contract, allowing parallel versions
    val version: String,
    // Operation to help consumers to filter the kind of message that it will process
    val operation: MessageOperation,
    // Message object serialized as JSON
    val `object`: String
) : Serializable