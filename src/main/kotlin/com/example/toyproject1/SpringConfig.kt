package com.example.toyproject1

import com.example.toyproject1.repository.JdbcMemberRepository
import com.example.toyproject1.repository.MemberRepository
import com.example.toyproject1.repository.MemoryMemberRepository
import com.example.toyproject1.service.MemberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class SpringConfig  {

    private var dataSource : DataSource

    @Autowired //(중요)
    constructor(dataSource: DataSource){    //스프링께서 만들어주신 db소스를 주입해줌.(DI)
        this.dataSource = dataSource
    }

    @Bean
    fun memberService() : MemberService{    //'MemberService' 타입의 인스턴스인 'memberservice'를 호출하여 'Bean'에 등록해줌.
        return MemberService(memberRepository())
    }

    @Bean
    fun memberRepository() : MemberRepository {
        //return MemoryMemberRepository() //인터페이스 X, 실체화만 리턴O -> 리턴하는애를 실제 디비로 리턴하기만 하면 바꿔 끼울 수 있다.
        return JdbcMemberRepository(dataSource)   //갈아끼웠음 (실제 디비로)
    }


}