package com.yolo.jean.domain.reply.entity

import com.yolo.jean.domain.board.entity.Board
import com.yolo.jean.domain.common.EntityAuditing
import javax.persistence.*

@Table(name = "reply")
@Entity
class Reply (
    author: String,
    title: String,
    content: String
): EntityAuditing(){

    @Column(name = "author", nullable = false)
    var author: String = author
        protected set

    @Column(name = "title", nullable = false)
    var title: String = title
        protected set

    @Column(name = "content", nullable = false)
    var content: String = content
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

    fun updateStatus(title: String, content: String) {
        this.title = title
        this.content = content
    }

    companion object {
        fun of(_author: String, _title: String, _content: String) = Reply(_author, _title, _content)
    }
}
