package com.yolo.dokotlin

import com.yolo.dokotlin.board.entity.Board
import com.yolo.dokotlin.board.entity.Reply
import com.yolo.dokotlin.board.repository.BoardRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class DokotlinApplication(var boardRepository: BoardRepository): CommandLineRunner {

	override fun run(vararg args: String?) {
		println("애플리케이션 실행한다!")

		for (index in 1..20) {
			val board = Board.of("임준영$index", "오늘은 몇일째$index", "하하$index")
			val reply1 = Reply.of("배성탑$index", "뽀삐코인가즈아$index", "뽀삐뽀삐뽀삐어$index")
			val reply2 = Reply.of("김유중$index", "도지코인가즈아$index", "도지도지되여$index")
			reply1.addBoard(board)
			reply2.addBoard(board)
			boardRepository.save(board)
		}
	}
}

fun main(args: Array<String>) {
	runApplication<DokotlinApplication>(*args)
}

