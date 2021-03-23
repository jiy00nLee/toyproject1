package com.example.toyproject1.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody


@Controller
class HelloController {

    //[1.정적 콘텐츠]
    @GetMapping("hello")
    fun hello (model : Model): String {
        model.addAttribute("data", "hello!!")
        return "hello"
    }

    //[2.Template Engine & MVC]
    @GetMapping("hello-mvc") //web-link (Async)
                        //(default value) required = true
    fun helloMVC(@RequestParam(value = "name") name_var: String, model:Model) : String { //따라서 웹('http://localhost:8080/hello-mvc)에서 name이라는 변수에 값을 주어야함. -> http://localhost:8080/hello-mvc?name=jiyoon
        model.addAttribute("name_var_html", name_var) //'변수' & '받은 값' (name_var=걍 내부 변수, name_var_html = html에서의 변수)
        return "hello-template" // 'hello-template'으로 가게 됨.
    }

    //[3. JSON -> STRING & API] (html X, http의 헤더&바디에서의 바디)
    @GetMapping("hello-string") //STRING
    @ResponseBody //중요
    fun hellostring(@RequestParam("name") name : String) : String {
        return "hello" + name                                            //html(템플릿) 이런거 없음. 걍 무식하게 웹에 바로 리턴함.('중요' 때문임)
    }

    @GetMapping("hello-api")    //API
    @ResponseBody
    fun helloApi(@RequestParam("name") name : String) : Hello {     //위 단순 string 출력해주는 애랑 다름. (API) <ex. http://localhost:8080/hello-api?name=jiyoon>
        var hello = Hello()
        hello.name = name
        return hello
    }

    class Hello { //코틀린은 getter, setter 필요 X
        lateinit var name : String
    }

    /*
    class Hello {   //코틀린's 중첩클래스
        private lateinit var name : String  //private 선언 중요

        fun getName() : String {
            return name  }

        fun setName(name_var : String){
            this.name = name_var
        }*/



}