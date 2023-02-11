package com.yolo.jean.api.kafka

/*import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.yolo.jean.domain.board.entity.Board
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class SampleTopicListener(
    private val mapper: ObjectMapper
) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @KafkaListener(topics = ["sample-topic"])
    fun consume(@Payload data: String) {
        log.info("Message: $data")
        val board = mapper.readValue(data, object: TypeReference<Board>() {})
        log.info("Board: $board")
    }
}*/
