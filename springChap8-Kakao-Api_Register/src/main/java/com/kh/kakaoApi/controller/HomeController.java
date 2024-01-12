package com.kh.kakaoApi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kh.kakaoApi.service.KakaoUserService;

import lombok.RequiredArgsConstructor;

@Controller
public class HomeController {
	private final KakaoUserService kakaoUserService;
	
	public HomeController(KakaoUserService kakaoUserService) {
		this.kakaoUserService = kakaoUserService;
	}
	
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("kakaoUrl", kakaoUserService.getKakaoLogin());
		return "index";
	}
}
