package com.example.toyproject1

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication 	//톰캣 웹서버를 내장하고 있음.
class Toyproject1Application

fun main(args: Array<String>) {
	runApplication<Toyproject1Application>(*args)
}
