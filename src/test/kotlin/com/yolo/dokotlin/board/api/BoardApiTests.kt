package com.yolo.dokotlin.board.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.yolo.dokotlin.board.model.BoardDto
import com.yolo.dokotlin.doc.ApiDocumentUtils
import com.yolo.dokotlin.doc.ApiDocumentUtils.Companion.getDocumentRequest
import com.yolo.dokotlin.doc.ApiDocumentUtils.Companion.getDocumentResponse
import com.yolo.dokotlin.doc.RestDocumentTests
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@SpringBootTest
class BoardApiTests : RestDocumentTests() {

    @DisplayName("WebMvc Controller 테스트")
    @Test
    fun 웹MVC_테스트를_수행한다() {
        // given
        val welcome = "코틀린 세계에 오신걸 환영합니다."

        // when / then
        mockMvc.perform(
            get("/api/v1/boards/test")
        ).andExpect(
            status().isOk
        ).andExpect(
            jsonPath("$.author").value("임준영")
        ).andExpect(
            jsonPath("$.title").value("람다 떡상 가즈아")
        ).andDo(
            document(
                "board",
                responseFields(
                    fieldWithPath("author")
                        .description("Board's User"),
                    fieldWithPath("title").description("Board's Title"),
                    fieldWithPath("content").description("Board's Content")
                )
            )
        );
    }

    @Test
    fun 게시판을_저장한다() {
        // given
        val mapper = ObjectMapper()

        val boardDto = BoardDto("임준영", "람다 가즈아!", "조만간 오를거야 화이팅!!")
        val content = mapper.writeValueAsString(boardDto)

        // when
        mockMvc.perform(
            post("/api/v1/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        ).andExpect(
                jsonPath("$.author").value(boardDto.author)
        ).andExpect(
                jsonPath("$.title").value(boardDto.title)
        ).andDo(
            document("board/save-api",
            getDocumentRequest(),
            getDocumentResponse(),
            requestFields(
                fieldWithPath("author").type(JsonFieldType.STRING).description("게시판 작성자"),
                fieldWithPath("title").type(JsonFieldType.STRING).description("게시판 제목"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("게시판 내용")
            ),
            responseFields(
                fieldWithPath("author").type(JsonFieldType.STRING).description("게시판 작성자"),
                fieldWithPath("title").type(JsonFieldType.STRING).description("게시판 제목"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("게시판 내용")
            ))
        )
    }
}
