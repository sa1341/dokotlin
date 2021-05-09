package com.yolo.dokotlin.board.entity

import lombok.AccessLevel
import lombok.Getter
import lombok.NoArgsConstructor
import javax.persistence.*

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "board")
@Entity
class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var author: String? = null
    var title: String? = null
    var content: String? = null
}
