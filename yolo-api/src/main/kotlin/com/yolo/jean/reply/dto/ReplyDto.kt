package com.yolo.jean.reply.dto

import com.yolo.jean.domain.reply.entity.Reply
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class ReplyDto(
    @field:NotBlank
    var author: String,
    @field:NotBlank
    var title: String,
    @field:NotBlank
    @field:Size(min = 2, max = 1000, message = "본문은 2~30자 이내여야 합니다.")
    var content: String
) {

    fun toEntity(): Reply {
        return Reply.of(author, title, content)
    }

    data class Res(
        val id: Long?,
        val author: String,
        val title: String,
        val content: String
    )

    data class UpdateForm(
        @field:NotBlank
        val title: String,
        @field:NotBlank
        @field:Size(min = 2, max = 1000, message = "본문은 2~30자 이내여야 합니다.")
        val content: String
    )
}





