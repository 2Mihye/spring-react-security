package com.kh.api.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kh.api.login.kakao.service.KakaoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
	private KakaoService kakaoService;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("kakaoUrl", kakaoService.getKakaoLogin());
		return "index";
	}
}
