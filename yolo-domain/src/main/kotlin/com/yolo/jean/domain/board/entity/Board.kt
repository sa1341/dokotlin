package com.yolo.jean.domain.board.entity

import com.yolo.jean.domain.common.EntityAuditing
import com.yolo.jean.domain.reply.entity.Reply
import java.time.format.DateTimeFormatter
import javax.persistence.*

@Table(name = "board")
@Entity
class Board (
    author: String,
    title: String,
    content: String
): EntityAuditing() {

    @Column(name = "author", nullable = false)
    var author: String = author
        protected set

    @Column(name = "title", nullable = false)
    var title: String = title
        protected set

    @Column(name = "content", nullable = false)
    var content: String = content
        protected set

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "board")
    var replies: MutableList<Reply> = mutableListOf()

    companion object {
        fun of (
            _author: String,
            _title: String,
            _content: String
        ): Board = Board(_author, _title, _content)
    }

    override fun toString(): String {
        return "Board(author='$author', title='$title', content='$content')"
    }
}
