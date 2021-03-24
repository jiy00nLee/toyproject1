package com.example.toyproject1.repository

import com.example.toyproject1.domain.Member
import javax.persistence.EntityManager

class JPAMemberRepository : MemberRepository {

    private val entityManager: EntityManager //내부적으로 데이터 소스를 들고 있어서 데이터통신 이런걸 다 처리해줌.

    constructor(entityManager: EntityManager){
        this.entityManager =entityManager
    }

    override fun save(member: Member): Member {
        entityManager.persist(member)
        return member
    }

    override fun findById(id: Long): Member? {
        val member: Member = entityManager.find(Member::class.java, id)
        return member
    }

    override fun findByName(name: String): Member? {
        val result :List<Member> = entityManager.createQuery("select m from Member m where m.name =:name", Member::class.java)
            .setParameter("name", name).resultList
        return result.randomOrNull()
    }

    override fun findAll(): List<Member> {
        //val result :List<Member> =  entityManager.createQuery("select m from Member m", Member::class.java).resultList
        //return result
                                                 //원래는 select id, name..해서 막 매핑 해줘야되는데 Entity는 이미 되어있어서 셀렉만 해주면 됨.
        return entityManager.createQuery("select m from Member m", Member::class.java).resultList //'m'==Entity 객체 자체를 셀렉.
    }
}