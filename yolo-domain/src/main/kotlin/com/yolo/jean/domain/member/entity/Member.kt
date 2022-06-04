package com.yolo.jean.domain.member.entity

import com.yolo.jean.domain.common.EntityAuditing
import javax.persistence.Entity

@Entity
class Member (
    email: String,
    name: String,
    age: Int
): EntityAuditing() {

    companion object {
        fun of(_email: String, _name: String, _age: Int): Member {
            return Member(_email, _name, _age)
        }
    }
}
