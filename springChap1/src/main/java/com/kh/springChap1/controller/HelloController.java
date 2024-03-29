package com.kh.springChap1.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // api로 전달해서 호출 @Controller는 html 파일을 바라보게 하고, RestController는 controller 안에있는 데이터를 전달
@RequestMapping("/api")
/*
	CrossOrigin : 쿠키나 세션 무언가 접속하는 것을 허용해줄 때 사용 ( 문은 열어주되 데이터는 없는 것 )
	allowCredentials : 브라우저에서 요청에 대한 응답을 할 때, 요청에 인증정보 (쿠키, HTTP 인증)를 포함할 것인지를 나타낸 것
	allowCredentials는 인증정보를 포함한 요청을 서버로 전송할 수 있게 해줌. 인증정보를 서로 주고 받을 수 있게 해주는 역할을 함.
	Spring Security를 사용할 때는 위 코드를 작성하지 않음.
 */
@CrossOrigin(origins="http://localhost:3000", allowCredentials="true")
public class HelloController {
	@GetMapping("/expression")
	public String getHello() {
		return "Amazing !";
	}
	@GetMapping("/java")
	public String getJava() {
		return "Spring boot test code";
	}
	
	// GetMapping react 라는 엔드포인트를 주고 react에서 api 호출하기
	@GetMapping("/react")
	public String getReact() {
		return "Connection with Java and React";
	}
}