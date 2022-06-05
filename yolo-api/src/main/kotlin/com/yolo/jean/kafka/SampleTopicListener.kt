package com.yolo.jean.kafka

import com.yolo.jean.domain.common.logger
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class SampleTopicListener {

    private val log by logger()

    @KafkaListener(topics = ["sample-topic"])
    fun consume(@Payload data: String) {
        log.info("Message: $data")
    }
}
