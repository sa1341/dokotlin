package com.yolo.dokotlin.board.service

import com.yolo.dokotlin.board.model.BoardDto
import com.yolo.dokotlin.board.repository.BoardRepository
import com.yolo.dokotlin.global.error.EntityNotFoundException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardService(private val boardRepository: BoardRepository) {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Transactional
    fun save(boardDto: BoardDto) {
        logger.debug("boardDto: $boardDto")
        val board = boardDto.toEntity()
        boardRepository.save(board)
    }

    @Transactional
    fun update(id: Long, updateForm: BoardDto.UpdateForm): BoardDto.Res? {
        return boardRepository.findByIdOrNull(id)?.let {
           it.updateStatus(updateForm)
           it.toRes()
        }?: kotlin.run {
            throw EntityNotFoundException("Board Id is not exist: $id")
        }
    }

    @Transactional(readOnly = true)
    fun findBoardById(id: Long): BoardDto.Res? {
        val board = boardRepository
            .findByIdOrNull(id)

        return board?.let {
            it.toRes()
        }?: kotlin.run {
            throw EntityNotFoundException("Board Id is not exist: $id")
        }
    }

    @Transactional
    fun delete(id: Long): Boolean {
        val board = boardRepository.findByIdOrNull(id)
        return board?.let {
            boardRepository.deleteById(id)
            true
        }?: kotlin.run {
            false
        }
    }
}
