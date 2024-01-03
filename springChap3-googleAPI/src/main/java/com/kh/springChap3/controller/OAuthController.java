package com.kh.springChap3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.springChap3.model.MemberGoogle;
import com.kh.springChap3.service.MemberGoogleService;

@Controller
@RequestMapping("/oauth")
public class OAuthController {
	@Autowired
	private MemberGoogleService memberGoogleService;
	
	@GetMapping("/loginSucess")
	public String loginSuccess(@AuthenticationPrincipal OAuth2User oauthUser, Model model) {
		String email = oauthUser.getAttribute("email");
		MemberGoogle user = memberGoogleService.findByUsername(email);
		
		if(user == null) {
			user = new MemberGoogle();
			user.setUsername(email);
			user.setEmail(email);
			memberGoogleService.saveMember(user);
			
			model.addAttribute("newUser", true);
		}
		
		return "loginSuccess";
	}
}