package com.yolo.dokotlin.board.model

import com.yolo.dokotlin.board.entity.Board

class BoardDto(val author: String, val title: String, val content: String) {

    fun toEntity(): Board {
        return Board.of(author, title, content)
    }

    override fun toString(): String {
        return "BoardDto(author='$author', title='$title', content='$content')"
    }

    class Res(val author: String, val title: String, val content: String)
}
