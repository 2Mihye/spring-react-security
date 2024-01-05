package com.kh.springChap4.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.springChap4.model.UserSNS;
import com.kh.springChap4.repository.UserRepository;
import com.kh.springChap4.service.GoogleService;
import com.kh.springChap4.service.UserService;

@Controller
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
