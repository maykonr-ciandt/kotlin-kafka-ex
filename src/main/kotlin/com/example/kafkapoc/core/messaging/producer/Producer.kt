package com.example.kafkapoc.core.messaging.producer

import com.example.kafkapoc.core.messaging.Message

/**
 * Interface to send messages to the sqs service
 *
 * @author Maykon Rissi
 * @since 2021-09-27
 */
interface Producer {

    /**
     * @param topic as the topic to send the message
     * @param message as the message to be sent
     */
    fun sendMessage(topic: String, message: Message)

}