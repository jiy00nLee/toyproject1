package com.example.toyproject1.controller

import com.example.toyproject1.service.MemberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller


@Controller
class MemberController {
    //var memberService : MemberService = MemberService() //객체를 새로 생성할 경우, 쓸데 없이 여러개가 생성되므로 에바세바.(X)
    var memberService : MemberService  //-> 스프링 컨테이너에 등록후 받아쓰게 해야함.

    @Autowired
    constructor(memberService: MemberService){
        this.memberService =memberService
    }


}


/*
@Controller
class MemberController @Autowired constructor(memberService: MemberService){

}
*/



