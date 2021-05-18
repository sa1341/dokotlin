package com.yolo.dokotlin.reply.dto

import com.yolo.dokotlin.reply.entity.Reply
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class ReplyDto(
    var id: Long?,
    @field:NotBlank
    var author: String,
    @field:NotBlank
    var title: String,
    @field:NotBlank
    @field:Size(min = 2, max = 1000, message = "본문은 2~30자 이내여야 합니다.")
    var content: String,
    var createdAt: String?,
    var updatedAt: String?
) {
    fun toEntity(): Reply {
        return Reply.of(author, title, content)
    }

    class UpdateForm(
        @field:NotBlank
        val title: String,
        @field:NotBlank
        @field:Size(min = 2, max = 1000, message = "본문은 2~30자 이내여야 합니다.")
        val content: String
    )

    class Res(val id: Long?, val author: String, val title: String, val content: String)
}
