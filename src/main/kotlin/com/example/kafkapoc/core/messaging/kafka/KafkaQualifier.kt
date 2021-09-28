package com.example.kafkapoc.core.messaging.kafka

/**
 * Qualifier to allow producer injections for Kafka
 *
 * Ex:
 * <pre>
 *     class SomeClass(@KafkaQualifier val producer: Producer)
 * </pre>
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class KafkaQualifier 