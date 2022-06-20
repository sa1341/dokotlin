package com.yolo.jean.board.dto

import com.yolo.jean.domain.board.entity.Board
import com.yolo.jean.reply.dto.ReplyDto
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class BoardDto(

    @field:NotBlank
    var author: String,

    @field:NotBlank
    var title: String,

    @field:NotBlank
    @field:Size(min = 2, max = 1000, message = "본문은 2~30자 이내여야 합니다.")
    var content: String
) {

    fun toEntity(): Board {
        return Board.of(author, title, content)
    }

    data class Res(val id: Long?, val author: String, val title: String,
                   val content: String, val createdAt: String, val updatedAt: String) {

        var replies: MutableList<ReplyDto> = mutableListOf()

        fun addReplies(replies: MutableList<ReplyDto>) {
            this.replies = replies
        }

        override fun toString(): String {
            return "Res(id=$id, author='$author', title='$title', content='$content')"
        }
    }

    data class UpdateForm(
        @field:NotBlank
        val title: String,
        @field:NotBlank
        @field:Size(min = 2, max = 1000, message = "본문은 2~30자 이내여야 합니다.")
        val content: String
    )
}

data class Result<T>(
    val data: T
)
