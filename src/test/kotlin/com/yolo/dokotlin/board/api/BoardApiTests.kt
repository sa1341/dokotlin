package com.yolo.dokotlin.board.api

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MockMvcBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class BoardApiTests {

     @Autowired
     lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var ctx: WebApplicationContext

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
            .addFilter<DefaultMockMvcBuilder?>(CharacterEncodingFilter("UTF-8", true))
            .build()
    }

    @DisplayName("WebMvc Controller 테스트")
    @Test
    fun 웹MVC_테스트를_수행한다() {
        // given
        val welcome = "코틀린 세계에 오신걸 환영합니다."

        // when / then
        mockMvc.perform(
            get("/api/v1/boards/test")
        ).andExpect (
            status().isOk
        ).andExpect (
            jsonPath("$.author").value("임준영")
        ).andExpect(
            jsonPath("$.title").value("람다 떡상 가즈아")
        ).andDo(print())
    }
}
