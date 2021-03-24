package com.example.toyproject1

import com.example.toyproject1.repository.*
import com.example.toyproject1.service.MemberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.sql.DataSource

@Configuration
class SpringConfig  {

    /*
    private var dataSource : DataSource

    @Autowired //(중요)
    constructor(dataSource: DataSource){    //스프링께서 만들어주신 db소스를 주입해줌.(DI)  -> Ver1,2,3 용
        this.dataSource = dataSource
    }*/

    /*
    //@PersistenceContext
    private var entityManager: EntityManager

    @Autowired
    constructor(entityManager: EntityManager){  //(ver4) JPA 용
        this.entityManager = entityManager
    }*/

    private var memberRepository : MemberRepository

    @Autowired
    constructor(memberRepository: MemberRepository){
        this.memberRepository = memberRepository       // (ver5) Data JPA 용
    }


    @Bean
    fun memberService() : MemberService{    //'MemberService' 타입의 인스턴스인 'memberservice'를 호출하여 'Bean'에 등록해줌.
        return MemberService(memberRepository)      // (ver5) Data JPA 용
    }
/*
    @Bean
    fun memberRepository() : MemberRepository { //인터페이스를 묶어주고 (밑에 실체화 리턴)
        //return MemoryMemberRepository() //인터페이스 X, 실체화(ver1) 만 리턴O -> 리턴하는애를 실제 디비로 리턴하기만 하면 바꿔 끼울 수 있다.

        //return JdbcMemberRepository(dataSource)   //갈아끼웠음 (ver2) (실제 디비로- JDBC original)

        //return JdbcTemplateMemberRepository(dataSource) //갈아끼웠음 (ver3) (JDBC Template)

        //return JPAMemberRepository(entityManager) //갈아끼웠음 (ver4) (JPA)


    }*/


}