package com.yolo.dokotlin.config

import com.yolo.dokotlin.board.entity.Board
import com.yolo.dokotlin.board.repository.BoardRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RequestPredicates.GET
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import java.net.URI

@Configuration
class RouterFunctionConfig(private val boardRepository: BoardRepository) {

    @Bean
    fun helloRouterFunction(): RouterFunction<*> {
         return route(GET("/api/hello")) {
             ok().body(just("Hello World!"), String::class.java)
         }
    }

    fun recents(request: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok()
            .body(boardRepository.findAll().take(12), Board::class.java)
    }

    fun postBoard(request: ServerRequest): Mono<ServerResponse> {
        val board: Mono<Board> = request.bodyToMono(Board::class.java)

        return ServerResponse
            .created(URI.create("http://localhost:8080/design/board/"))
            .body(board, Board::class.java)
    }
}
