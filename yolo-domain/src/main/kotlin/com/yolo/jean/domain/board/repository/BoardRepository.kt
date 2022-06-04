package com.yolo.jean.domain.board.repository

import com.yolo.jean.domain.board.entity.Board
import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository: JpaRepository<Board, Long> {
}
