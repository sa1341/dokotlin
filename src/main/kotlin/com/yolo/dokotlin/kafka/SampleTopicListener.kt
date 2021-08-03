package com.yolo.dokotlin.kafka

import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class SampleTopicListener {

    val logger = LoggerFactory.getLogger(SampleTopicListener::class.java)

    @KafkaListener(topics = ["sample-topic"])
    fun consume(@Payload data: String) {
        logger.info("Message: $data")
    }
}
