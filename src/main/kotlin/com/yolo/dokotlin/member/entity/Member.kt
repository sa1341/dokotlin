package com.yolo.dokotlin.member.entity

import com.yolo.dokotlin.global.common.model.BaseTimeEntity
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Member private constructor(_email: String, _name: String, _age: Number): BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    var email: String = _email
    var name: String = _name
    var age: Number = _age

    companion object {
        fun of(_email: String, _name: String, _age: Number): Member {
            return Member(_email, _name, _age)
        }
    }
}
