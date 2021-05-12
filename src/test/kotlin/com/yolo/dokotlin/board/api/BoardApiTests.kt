package com.yolo.dokotlin.board.api

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class BoardApiTests {

    @Autowired
    lateinit var mockMvc: MockMvc

    @DisplayName("Eed Point 테스트")
    @Test
    fun 게시판_API_테스트를_수행한다() {
        val welcome = "코틀린 세계에 오신걸 환영합니다."
        mockMvc.perform(get("/api/v1/boards/test"))
            .andExpect(status().isOk)
            .andExpect(content().string(welcome))
    }
}
