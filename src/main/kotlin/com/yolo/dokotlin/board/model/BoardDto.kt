package com.yolo.dokotlin.board.model

import com.yolo.dokotlin.board.entity.Board
import com.yolo.dokotlin.reply.dto.ReplyDto
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class BoardDto(

    @field:NotBlank
    var author: String,

    @field:NotBlank
    var title: String,

    @field:NotBlank
    @field:Size(min = 2, max = 1000, message = "본문은 2~30자 이내여야 합니다.")
    var content: String,

    ) {
    fun toEntity(): Board {
        return Board.of(author, title, content)
    }

    class UpdateForm(
        @field:NotBlank
        val title: String,
        @field:NotBlank
        @field:Size(min = 2, max = 1000, message = "본문은 2~30자 이내여야 합니다.")
        val content: String
    )

    class Res(val id: Long?, val author: String, val title: String, val content: String, val createdAt: String?, val updatedAt: String?) {
        var replies: MutableList<ReplyDto> = mutableListOf()

        fun addReplies(replies: MutableList<ReplyDto>) {
            this.replies = replies
        }
    }
}
