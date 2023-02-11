package com.yolo.jean.api.board.dto

import com.yolo.jean.api.reply.dto.ReplyDto
import com.yolo.jean.domain.board.entity.Board
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class BoardDto(

    @field:NotBlank
    var author: String,

    @field:NotBlank
    var title: String,

    @field:NotBlank
    @field:Size(min = 2, max = 1000, message = "본문은 2~30자 이내여야 합니다.")
    var content: String,

    @field:NotNull
    var memberId: Long
) {

    fun toEntity(): Board {
        return Board.of(author, title, content)
    }

    data class Res(
        val id: Long?,
        val author: String,
        val title: String,
        val content: String,
        val createdAt: String,
        val updatedAt: String
    ) {

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

class Result<T>(t: T) {
    val data = t
}
