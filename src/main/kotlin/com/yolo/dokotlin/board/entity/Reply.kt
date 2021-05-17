package com.yolo.dokotlin.board.entity

import com.yolo.dokotlin.board.dto.ReplyDto
import com.yolo.dokotlin.global.common.model.BaseTimeEntity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.persistence.*

@Table(name = "reply")
@Entity
class Reply private constructor(
    _author: String,
    _title: String,
    _content: String
) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "author", nullable = false)
    var author: String = _author
        protected set

    @Column(name = "title", nullable = false)
    var title: String = _title
        protected set

    @Column(name = "content", nullable = false)
    var content: String = _content
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId", nullable = false)
    var board: Board? = null
        protected set

    fun addBoard(board: Board) {
        if (this.board != null) {
            this.board!!.replies.remove(this)
        }
        this.board = board
        board.replies.add(this)
    }

    companion object {
        fun of(_author: String, _title: String, _content: String) = Reply(_author, _title, _content)
    }

    fun toRes(): ReplyDto {
        val createdAt = parseDateTimeToStr(createdDate);
        val updatedAt = parseDateTimeToStr(modifiedDate);
        return ReplyDto(id, author, title, content, createdAt, updatedAt);
    }

    fun parseDateTimeToStr(localDateTime: LocalDateTime?): String? {
        return localDateTime?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    }
}
