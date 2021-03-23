package com.example.toyproject1.service

import com.example.toyproject1.domain.Member
import com.example.toyproject1.repository.MemoryMemberRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.fail

internal class MemberServiceTest {

    lateinit var memberService : MemberService
    lateinit var memberRepository : MemoryMemberRepository

    @BeforeEach
    fun beforeEach(){   //테스트 실행할 때마다 생성해줌.
        memberRepository = MemoryMemberRepository()
        memberService = MemberService(memberRepository)
    }

    @AfterEach
    fun afterEach(){
        memberRepository.clearStore()
    }

    @Test
    fun 회원가입() {
        //given
        var member : Member = Member()
        member.name = "이지윤"

        //when
        var saveId : Long = memberService.join(member)

        //then
        var findMember : Member? = memberService.findOne(saveId)
        if (findMember != null) {
            Assertions.assertThat(member.name.equals(findMember.name))
        }
    }

    @Test
    fun 중복_회원_예외() {

        //given
        var member1 : Member = Member()
        member1.name = "이지윤"

        var member2 : Member = Member()
        member2.name = "이지윤"

        //when
        memberService.join(member1)
        //[예외처리 테스트 방법 1]
        try {memberService.join(member2)
            fail()
        }catch (e: IllegalStateException ){
            Assertions.assertThat(e.message.equals("이미 존재하는 회원입니다.")) //에러가 난 경우가 잘 작동하는 지 확인.
        }
    }


    @Test
    fun 아이디회원찾기() {
    }
}