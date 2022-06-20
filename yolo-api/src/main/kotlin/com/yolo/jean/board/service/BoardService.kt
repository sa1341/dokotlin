package com.yolo.jean.board.service

import com.yolo.jean.board.dto.BoardDto
import com.yolo.jean.board.dto.Result
import com.yolo.jean.domain.board.entity.Board
import com.yolo.jean.domain.board.repository.BoardRepository
import com.yolo.jean.domain.member.repository.MemberRepository
import com.yolo.jean.global.common.util.DateUtils
import com.yolo.jean.global.error.exception.EntityNotFoundException
import com.yolo.jean.reply.dto.ReplyDto
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardService(
    private val boardRepository: BoardRepository,
    private val memberRepository: MemberRepository
) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Transactional(readOnly = true)
    fun findBoardById(id: Long): BoardDto.Res {

        val board = boardRepository
            .findByIdOrNull(id)

       return board?.let {
            BoardDto.Res(it.id, it.author, it.title, it.content,
                DateUtils.parseCreatedDt(it.createdAt), DateUtils.parseUpdatedDt(it.updatedAt))
        }?: kotlin.run {
            throw EntityNotFoundException("해당 게시판이 존재하지 않습니다.: $id")
        }
    }

    @Transactional
    fun save(boardDto: BoardDto) {
        log.info("boardDto: $boardDto")
        val board = boardDto.toEntity()
        boardRepository.save(board)
    }

    @Transactional
    fun update(id: Long, updateForm: BoardDto.UpdateForm): Board? {
        return boardRepository.findByIdOrNull(id)?.let {
            it
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

    @Transactional
    fun getBoardsWithReplies(email: String): Result<*> {

        val member = memberRepository.findMemberByEmail(email)

        val res = member?.let { it ->
            it.boards.map { it ->
                val boardRes = BoardDto.Res(
                    it.id,
                    it.author,
                    it.title,
                    it.content,
                    DateUtils.parseCreatedDt(it.createdAt),
                    DateUtils.parseUpdatedDt(it.updatedAt)
                )

                val replies = it.replies.map {
                    ReplyDto(it.author, it.title, it.content)
                }.toMutableList()

                boardRes.addReplies(replies)
                boardRes
            }.toList()
        } ?: kotlin.run {
            throw EntityNotFoundException("회원의 이메일이 존재하지 않습니다. $email")
        }

        return Result(res)
    }

    private fun getBoardFallback(): String {
        return "게시글이 없는거 같아요";
    }
}
