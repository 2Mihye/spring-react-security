package com.kh.springChap5.controller;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.kh.springChap5.model.Member;

@Controller
public class SNSController {
	@GetMapping("/oauth2/authorization/naver")
	public String naverLogin() {
		return "redirect:/login/oauth2/code/naver";
	}
	
	@GetMapping("/login/oauth2/code/naver")
	public String naverLoginCallback(@AuthenticationPrincipal OAuth2User oauth2User, Model model) {
		Object response = oauth2User.getAttribute("response");
		// instanceof : 객체가 인스턴스인지 아닌지 판단해줌.
		if(response instanceof Map) {
			Member naverMember = new Member();
			naverMember.setEmail(email);
			naverMember.setFullName(name);
			naverMember.setUsername("");
			model.addAttribute("member", naverMember);
			return "register";
		}
	}
}
