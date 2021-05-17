package com.yolo.dokotlin.board.repository

import com.yolo.dokotlin.board.entity.Board
import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository: JpaRepository<Board, Long>
