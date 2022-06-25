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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
class BoardApiTests: RestDocumentTests() {

    @Autowired
    lateinit var objectMapper: ObjectMapper

    val memberId: Long = 1

    @DisplayName("BoardApi TEST01 - 게시글 조회 테스트")
    @Test
    fun 게시글을_조회한다() {

        // given
        val boardId: Long = 18;
        val responseBody = BoardDto("임준영", "22년도 대박기원", "주식도 흥하자!", memberId)
        mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/v1/boards/{id}", boardId)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            status().isOk
        ).andExpect(
            jsonPath("$.author").value(responseBody.author)
        ).andExpect(
            jsonPath("$.title").value(responseBody.title)
        ).andExpect(
            jsonPath("$.content").value(responseBody.content)
        ).andDo(
            document("board/getBoard",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                    parameterWithName("id").description("게시글 Id")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시글 Id"),
                    fieldWithPath("author").type(JsonFieldType.STRING).description("게시글 작성자"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("게시글 생성일자"),
                    fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("게시글 갱신일자"),
                    fieldWithPath("replies").type(JsonFieldType.ARRAY).description("게시글 댓글목록")
                )
            )
        )
    }

    @DisplayName("BoardApi TEST02 - 게시글 저장 테스트")
    @Test
    fun 게시글을_저장한다() {

        // given
        val boardDto = BoardDto("임준영", "화이팅하자..", "증권 대박나즈아!!!", memberId)
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
                    fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
                    fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별번호")
                ),
                responseFields(
                    fieldWithPath("author").type(JsonFieldType.STRING).description("게시글 작성자"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
                    fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별번호")
                )
            )
        )
    }
}
