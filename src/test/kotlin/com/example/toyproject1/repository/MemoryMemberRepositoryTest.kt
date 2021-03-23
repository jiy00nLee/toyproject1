package com.example.toyproject1.repository

import com.example.toyproject1.domain.Member
import org.junit.jupiter.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

class MemoryMemberRepositoryTest {

    val repository : MemoryMemberRepository = MemoryMemberRepository() //-> MemberRepository text X

    @AfterEach
    fun afterEachTest(){    //테스트 시에 공용데이터 제거가 필요하다.
        repository.clearStore()
    }


    @Test
    fun save(){
        var member : Member = Member()
        member.name = "spring"

        repository.save(member) //새 멤버를 추가해줌.
        repository.findById(member.id) // (위) 멤버의 id를 가져옴.

        var result : Member? = repository.findById(member.id)

        //[테스팅방법 1. 글로 출력]
        System.out.println("result = " + (result==member))

        //[테스팅방법 2-1. 상태결과로 표현]
        //Assertions.assertEquals(member, result) //(기대하는 것과 실제)
        //[테스팅방법 2-2. 상태결과로 표현]
        assertThat(member).isEqualTo(result)

    }

    @Test
    fun findByName(){
        var member1 : Member = Member()
        member1.name="spring1"
        repository.save(member1)

        var member2 : Member = Member()
        member2.name="spring2"
        repository.save(member2)

        var result : Member? = repository.findByName("spring1")

        assertThat(result).isEqualTo(member1)

    }

    @Test
    fun findAll(){
        var member1 : Member = Member()
        member1.name="spring1"
        repository.save(member1)

        var member2 : Member = Member()
        member2.name="spring2"
        repository.save(member2)

        var result : List<Member> = repository.findAll()
        assertThat(result.size).isEqualTo(2)
    }




}