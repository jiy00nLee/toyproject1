package com.example.toyproject1.domain

import javax.persistence.*


@Entity
class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //DB에서 알아서 생성해서 넣어주는 전략.
    var id : Long =0
    //@Column(name = "Username")
    lateinit var name : String
}