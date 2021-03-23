package com.example.toyproject1.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {

    @GetMapping("/") //(도메인일때) 아무것도 없어도 호출.
    fun home() : String {
        return "home" // 'home.html'이 호출됨.
    }
}