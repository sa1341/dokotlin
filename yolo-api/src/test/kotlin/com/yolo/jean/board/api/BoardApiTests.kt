package com.yolo.jean.board.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.yolo.jean.board.dto.BoardDto
import com.yolo.jean.doc.ApiDocumentUtils.Companion.getDocumentRequest
import com.yolo.jean.doc.ApiDocumentUtils.Companion.getDocumentResponse
import com.yolo.jean.doc.RestDocumentTests
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
class BoardApiTests: RestDocumentTests() {

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @DisplayName("BoardApi TEST01 - 게시글 저장 테스트")
    @Test
    fun 게시글을_저장한다() {

        // given
        val boardDto = BoardDto("진캄", "22년도 대박기원", "주식도 흥하자!")
        val content = objectMapper.writeValueAsString(boardDto)

        mockMvc.perform(
            post("/api/v1/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        ).andExpect(
            status().isOk
        ).andExpect(
            jsonPath("$.author").value(boardDto.author)
        ).andExpect(
            jsonPath("$.title").value(boardDto.title)
        ).andDo(
            document("board/save-board",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("author").type(JsonFieldType.STRING).description("게시글 작성자"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용")
                ),
                responseFields(
                    fieldWithPath("author").type(JsonFieldType.STRING).description("게시글 작성자"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용")
                )
            )
        )
    }
}
