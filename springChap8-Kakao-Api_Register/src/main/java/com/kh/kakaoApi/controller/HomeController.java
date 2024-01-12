package com.kh.kakaoApi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kh.kakaoApi.service.KakaoUserService;
import com.kh.kakaoApi.vo.KakaoUser;

import jakarta.servlet.http.HttpSession;
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
	
	// 현재는 Kakao로만 로그인하지만 추후 sns인증을 거치지 않은 회원가입일 경우를 생각하여 GetMapping을 한 번에 적어주는 것 = 공통적인 로그인 부분은 홈으로 넣겠다는 의미
	@GetMapping("/main")
	public String home(Model model, HttpSession session) {
		KakaoUser inUser = (KakaoUser) session.getAttribute("InUser");
				model.addAttribute("InUser", inUser);
		return "main";
	}
	
}
