package com.example.toyproject1.service

import com.example.toyproject1.domain.Member
import com.example.toyproject1.repository.MemberRepository
import com.example.toyproject1.repository.MemoryMemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.IllegalStateException


@Service
class MemberService {

    //val memberRepository : MemberRepository = MemoryMemberRepository() //인터페이스, real class(실체)
    var memberRepository : MemberRepository //(디테일!) Test에서의 인스턴스와 같아야 함.

    @Autowired
    constructor( memberRepository : MemberRepository){
        this.memberRepository = memberRepository
    }

    //[1. 회원가입 서비스]
    fun join(member : Member) : Long {

        validateDuplicateMember(member) //같은 이름이 있는 중복 회원 -> X

        memberRepository.save(member)

        return member.id
    }

    //[1-1. 중복회원 검거 서비스]
    fun validateDuplicateMember(member: Member){
         var result : Member? = memberRepository.findByName(member.name)
         if (result != null){
             throw IllegalStateException("이미 존재하는 회원입니다.")
         }
         //result.takeIf { member -> throw IllegalStateException("이미 존재하는 회원입니다.")  } Null이 아닌 객체만 .takeIf를 사용할 수 있다.
        //memberRepository.findByName(member.name).takeIf { member -> throw IllegalStateException("이미 존재하는 회원입니다.")  }
    }

    //[2-1. 회원 검색 서비스 - 전체 츌룍]
    fun findMembers() : List<Member> {
        return memberRepository.findAll()
    }

    //[2-2. 회원 검색 서비스 - 아이디 검색 후 출력]
    fun findOne(memberId : Long) : Member? {
        return memberRepository.findById(memberId)
    }


}