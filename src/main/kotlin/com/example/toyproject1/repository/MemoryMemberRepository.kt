package com.example.toyproject1.repository

import com.example.toyproject1.domain.Member
import org.springframework.stereotype.Repository
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


//@Repository
class MemoryMemberRepository : MemberRepository { //Interface 구현하는 실체 클래스 (나중에 변경하거나 모듈성 생각하면 이방식이 좋다)

    companion object{
        private var store : MutableMap<Long, Member> = HashMap<Long, Member>() //Kotlin -> Map/ MutableMap
        private var sequence : Long = 0L
    }

     fun clearStore(){   //(테스팅용) 데이터 제거[맵데이터 클리어]
        store.clear()
    }

    override fun save(member: Member): Member {
        member.id = (++sequence)
        store.put(member.id,  member)
        return member
    }

    override fun findById(id: Long): Member? {
        return store.get(id) //Optional을 벗겨주어야함. (!)
    }

    override fun findByName(Name: String): Member? {   //하나만 뽑아내고 싶다.
        //return store.filterValues { member -> member.name.equals(Name)  }
        return store.values.find { member -> member.name.equals(Name)}
    //  return store.values.find { member -> member.name.equals(Name)}    List가 반환이 됨.
    }

    override fun findAll(): List<Member> {
        return ArrayList(store.values)
    }

}