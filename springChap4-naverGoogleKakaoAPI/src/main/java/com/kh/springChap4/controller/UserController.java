package com.kh.springChap4.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.springChap4.model.UserSNS;
import com.kh.springChap4.repository.UserRepository;
import com.kh.springChap4.service.GoogleService;
import com.kh.springChap4.service.UserService;

// @Controller
@RestController
/*
	@CrossOrigin() : 각 컨트롤러마다 바라보는 url이 다를 수 있기 때문에 어떤 도메인을 허용해줄 지 작성해주는 공간.
	origins = : 에 작성해준 도메인의 요청을 허용. 하나의 도메인이 아니라 배열처리하여 다수의 도메인을 넣을 수 있다
	allowCredentials = : 인증된 사용자의 쿠키를 요청에 포함할 수 있도록 허용할 지에 대한 여부를 허용해주는 것.
	allowedHeaders= : 허용할 수 있는 Header를 지정하고 *표시는 모든 Header를 허용한다는 뜻
					("-Requested-With", "Origins", "Content-type", "Accept", "authorization");
	methods : 원하는 메서드만 설정해서 받을 수 있도록 한번 더 처리할 수 있다.
			 {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
 */
@CrossOrigin(origins="http://localhost:3000", allowCredentials="true", allowedHeaders="*")
public class UserController {
	private final UserService userService;
	private final GoogleService googleService;
	
	@Autowired
	public UserController(UserService userService, GoogleService googleService) {
		this.userService = userService;
		this.googleService = googleService;
	}
	
	
	@GetMapping("/login")
	public String loginPage() {
		return "index";
	}
	
	// 구글 로그인을 위한 URL 추가
	@GetMapping("/oauth2/authorization/google")
	public String googleLogin() {
		return "redirect:/oauth2/authorization/google";
	}
	
	// 네이버 로그인을 위한 URL 추가
	@GetMapping("/oauth2/authorization/naver")
	public String naverLogin() {
		return "redirect:/oauth2/authorization/naver";
	}
	
	/*
	Google일 때
	
	@GetMapping("/loginSuccess")
	public String loginSuccess(@AuthenticationPrincipal OAuth2User principal, Model model) {
		
		System.out.println("OAuth2User Attributes : " + principal.getAttributes());
		
		model.addAttribute("name", principal.getAttribute("name"));
		model.addAttribute("email", principal.getAttribute("email"));
		
		return "loginSuccess";
	}*/
	
	// naver일 때
	@GetMapping("/loginSuccess")
	public String naverLoginSuccess(@AuthenticationPrincipal OAuth2User principal,
			@RequestParam(value="naverResponse", required=false) String naverResponse,
			Model model) {
		
		userService.naverLoginService(principal, naverResponse, model);
		
		return "loginSuccess";
	}
	
	// google일 때
	@GetMapping("/loginSuccessGoogle")
	public String googleLoginSuccess(@AuthenticationPrincipal OAuth2User principal, Model model) {
		googleService.googleLoginService(principal, model);
		
		System.out.println("OAuth2User Attributes : " + principal.getAttributes());
		
		model.addAttribute("name", principal.getAttribute("name"));
		model.addAttribute("email", principal.getAttribute("email"));
		
		return "loginSuccess";
	}
	
	@GetMapping("/loginSuccessKakao")
	public String loginSuccess(@AuthenticationPrincipal OAuth2User principal, Model model) {
		
		model.addAttribute("name", principal.getAttribute("name"));
		model.addAttribute("email", principal.getAttribute("email"));
		
		return "loginSuccess";
	}
}
