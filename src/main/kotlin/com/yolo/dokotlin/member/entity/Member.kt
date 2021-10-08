package com.yolo.dokotlin.member.entity

import com.yolo.dokotlin.global.common.model.BaseTimeEntity
import javax.persistence.*

@Entity
class Member private constructor(_email: String, _name: String, _age: Int): BaseTimeEntity() {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    @Id
    val id: Long? = null

    var email: String = _email

    var name: String = _name

    var age: Int = _age

    companion object {
        fun of(_email: String, _name: String, _age: Int): Member {
            return Member(_email, _name, _age)
        }
    }
}
