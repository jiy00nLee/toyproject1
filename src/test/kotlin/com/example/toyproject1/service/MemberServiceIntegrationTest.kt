package com.example.toyproject1.service

import com.example.toyproject1.domain.Member
import com.example.toyproject1.repository.MemberRepository
import com.example.toyproject1.repository.MemoryMemberRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Commit
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.Throws

//스프링 통합 테스트!!
@SpringBootTest // (2)
@Transactional // (2) -> 얘가 디비에 데이터가 남지 않게 해줌. (테스트 케이스에 이 애노테이션이 있으면, 테스트 시작 전에 트랜잭션을 시작하고,
// 테스트 완료 후에 항상 롤백한다. 이렇게 하면 DB에 데이터가 남지 않으므로 다음 테스트에 영향을 주지  않는다.
class MemberServiceIntegrationTest {

    @Autowired
    lateinit var memberService : MemberService
    @Autowired //밑에 묶어놨던걸 'test'하기 위해서 또 묶어줌. (2)
    lateinit var memberRepository : MemberRepository //(중요) Configuration에서 인터페이스와 실체화를 의존으로 묶어놔서 이게 됨.(1)

    /* 원래는 이처럼 'constructor'로 만들어서 해주는게 좋지만, test의 경우 여기서 한번 쓰고 말 것이기 때문에 위처럼 '의존성관계(DI)'를 집어넣어줌.
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
*/

    @Test
    @Commit // 관계형 데이터베이스 관리 시스템(RDBMS)에서 트랜잭션을 종료하고 다른 사용자에게 변경된 모든 사항을 보이도록 만드는 문.
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
            org.junit.jupiter.api.Assertions.fail()
        }catch (e: IllegalStateException ){
            Assertions.assertThat(e.message.equals("이미 존재하는 회원입니다.")) //에러가 난 경우가 잘 작동하는 지 확인.
        }
    }


    @Test
    fun 아이디회원찾기() {
    }
}
