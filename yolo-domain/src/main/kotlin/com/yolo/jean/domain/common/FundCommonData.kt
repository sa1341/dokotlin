package com.yolo.jean.domain.common

import com.yolo.jean.domain.board.entity.Board
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FundCommonData {

    @Bean
    fun testData(): Board {
        println("빈초기화 고고")
        return Board.of(
            _author = "임준영",
            _title = "함께 자라기",
            _content = "애자일문화"
        )
    }
}
