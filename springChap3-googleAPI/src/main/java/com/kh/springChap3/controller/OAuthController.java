package com.kh.springChap3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.kh.springChap3.model.UserGoogle;
import com.kh.springChap3.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/oauth")
public class OAuthController {
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/loginSuccess")
	public String loginSuccess(@AuthenticationPrincipal OAuth2User oauthUser, Model model) {
		String email = oauthUser.getAttribute("email");
		UserGoogle user = userService.findByUsername(email);
		
		System.out.println("OAuth2User: " + oauthUser); // 제대로 정보를 담아 출력하고 있는지 확인하기 위해 System.out.println을 찍어볼 것 !
		System.out.println("이메일 속성: " + email);
		
		if (user == null) {
			user = new UserGoogle();
			user.setUsername(email);
			user.setEmail(email);
			userService.saveUser(user);
			
			model.addAttribute("newUser", true);
		}
		model.addAttribute("email", email);
		return "loginSuccess";
	}
	
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/";
	}
}
