package com.yolo.dokotlin.board.entity

import com.yolo.dokotlin.global.entity.BaseTimeEntity
import javax.persistence.*

@Table(name = "board")
@Entity
class Board private constructor(
    _author: String,
    _title: String,
    _content: String
): BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "author", nullable = false)
    var author: String = _author

    @Column(name = "title", nullable = false)
    var title: String = _title

    @Column(name = "content", nullable = false)
    var content: String = _content

    companion object {
        fun of (
            _author: String,
            _title: String,
            _content: String
        ): Board = Board(_author, _title, _content)
    }
}
