package com.yolo.dokotlin.board.entity

import com.yolo.dokotlin.board.model.BoardDto
import com.yolo.dokotlin.global.common.model.BaseTimeEntity
import javax.persistence.*

@Table(name = "board")
@Entity
class Board(
    _author: String,
    _title: String,
    _content: String
): BaseTimeEntity() {

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


    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "board")
    var replies: MutableList<Reply> = mutableListOf()

    companion object {
        fun of (
            _author: String,
            _title: String,
            _content: String
        ): Board = Board(_author, _title, _content)
    }

    fun toRes(): BoardDto.Res {
        return BoardDto.Res(author, title, content)
    }

    fun updateStatus(updateForm: BoardDto.UpdateForm) {
        this.title = updateForm.title
        this.content = updateForm.content
    }
}
