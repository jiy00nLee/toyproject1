package com.example.toyproject1.repository

import com.example.toyproject1.domain.Member
import org.springframework.data.jpa.repository.JpaRepository

interface SpringDataJpaMemberRepository : JpaRepository<Member, Long>, MemberRepository { //Long은 'Member'의 id(pk)

    //(커스텀 방법) sql : select m from Member m where m.name=? 이 됨. -> findByNameAndId(Name:Sring, id:Long)
    override fun findByName(Name: String): Member?  //커스텀 메서드 (공통 메소드 X) 인 경우 -> 구현 필요.

}
//개신기 -> 'JpaRepository'는 내장되어있는 메소드.
//따라서 인터페이스만 구현했지만 이는 implement하고 있는 친구 덕분에 config 굳이 안해도 여기서 인터페이스에 대한 구현체를 알아서 만들어서 spring bean에 등록함.
//(뭘등록? 'MemberRepository요')