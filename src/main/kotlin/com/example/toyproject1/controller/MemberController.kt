package com.example.toyproject1.controller

import com.example.toyproject1.domain.Member
import com.example.toyproject1.service.MemberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping


//Get -> 조회할 때, Post-> 전달할 때.
@Controller
class MemberController {
    //var memberService : MemberService = MemberService() //객체를 새로 생성할 경우, 쓸데 없이 여러개가 생성되므로 에바세바.(X)
    private var memberService : MemberService  //-> 스프링 컨테이너에 등록후 받아쓰게 해야함.

    @Autowired
    constructor(memberService: MemberService){
        this.memberService =memberService
    }

    @GetMapping("/members/new") // "/members/new"가 들어올 경우 "members/createMemberForm"의 html로 이동. (그냥 html로 이동하는 함수)
    fun createForm() : String {
        return "members/createMemberForm"
    }

    @PostMapping("/members/new") // "/members/new"에서 나갈(전송) 경우 "members/createMemberForm"의 html로 이동.
    fun create(form : MemberForm) : String {
        val member : Member = Member()
        member.name= form.name // 입력받은 것(이름) == form.name
        // System.out.println("member= " + member.name)
        memberService.join(member)
        return "redirect:/"         //그 전 페이지로 돌아감.
    }

    @GetMapping("/members")
    fun list(model: Model) : String {
        val members : List<Member> = memberService.findMembers()
        model.addAttribute("members",members) //공부하기.
        return "members/memberList"
    }


}


/*
@Controller
class MemberController @Autowired constructor(memberService: MemberService){

}
*/



