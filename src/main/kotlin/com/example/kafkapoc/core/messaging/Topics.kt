package com.example.kafkapoc.core.messaging

/**
 * Existing topic names
 *
 * @author Maykon Rissi
 * @since 2021-09-27
 * @param mapper to serialize the object into a json string
 */
object Topics {
    const val BASE_TOPIC = "kafkapoc_"
    const val LOCATION_TOPIC = BASE_TOPIC + "locations"
    const val ROAD_TOPIC = BASE_TOPIC + "roads"
}