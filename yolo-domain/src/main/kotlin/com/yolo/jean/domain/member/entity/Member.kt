package com.yolo.jean.domain.member.entity

import com.yolo.jean.domain.board.entity.Board
import com.yolo.jean.domain.common.EntityAuditing
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.OneToMany

@Entity
class Member (
    var email: String,
    var name: String,
    var age: Int
): EntityAuditing() {

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "member")
    var boards: MutableList<Board> = mutableListOf()

    companion object {
        fun of(_email: String, _name: String, _age: Int): Member {
            return Member(_email, _name, _age)
        }
    }
}
