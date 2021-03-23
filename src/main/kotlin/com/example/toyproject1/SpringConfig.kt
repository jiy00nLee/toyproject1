package com.example.toyproject1

import com.example.toyproject1.repository.MemberRepository
import com.example.toyproject1.repository.MemoryMemberRepository
import com.example.toyproject1.service.MemberService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SpringConfig  {

    @Bean
    fun memberService() : MemberService{    //'MemberService' 타입의 인스턴스인 'memberservice'를 호출하여 'Bean'에 등록해줌.
        return MemberService(memberRepository())
    }

    @Bean
    fun memberRepository() : MemberRepository {
        return MemoryMemberRepository() //인터페이스 X, 실체화만 리턴O -> 리턴하는애를 실제 디비로 리턴하기만 하면 바꿔 끼울 수 있다.
    }


}