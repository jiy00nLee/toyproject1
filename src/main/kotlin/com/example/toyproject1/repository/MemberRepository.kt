package com.example.toyproject1.repository

import com.example.toyproject1.domain.Member
import java.util.*

interface MemberRepository {

    fun save(member : Member) : Member
    fun findById(id : Long) : Member?
    fun findByName(Name : String) : Member? // 자바 -> Optional : 'Null'값일 수도 있으니까 Null을 그대로 반환하는 것이 아닌 감싸서 null을 반환하게 함.
    fun findAll(): List<Member>

}