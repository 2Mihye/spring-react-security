package com.kh.springChap4.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.springChap4.model.UserSNS;
import com.kh.springChap4.repository.UserRepository;

@Service
public class GoogleService {	
		// 2. repository
		private final UserRepository userRepository;
		
		@Autowired // Autowired나 선언하는 녀석들은 항상 위에 있도록 해줘야 함 !
		public GoogleService(UserRepository userRepository){
			this.userRepository = userRepository;
		}
		
		public void googleLoginService(@AuthenticationPrincipal OAuth2User principal,
				Model model) {
			model.addAttribute("name", principal.getAttribute("name"));
			model.addAttribute("email", principal.getAttribute("email"));
		
		System.out.println("OAuth2User Attributes : " + principal.getAttributes());
		String name = null;
		String email = null;

		
		// OAuth2User에서 이름과 이메일 추출 (Google 과 Naver 둘 다 쓸 수 있음)
		if(name == null || email == null) {
			String principalName = principal.getName(); // Name 안에 이름과 이메일 등이 모두 들어간다.
			// principal.getName(); 로 가져온 정보 안에 이름과 이메일 등이 모두 들어가지만 이름과 이메일만 출력할 것
			// replaceAll() : 문자열에서 공백이나 숫자 등 패턴을 찾을 때 도와주는 식
			String[] keyValue = principalName.replaceAll("[{}]", "").split(",");
			for(String pair : keyValue) {
				String[] entry = pair.split("=");
				if(entry.length == 2) {
					String key = entry[0].trim();
					String value = entry[1].trim();
					if("name".equals(key)) {
						name = value;
					} else if ("email".equals(key)) {
						email = value;
					}
				}
			}
		}
		
		String provider = principal.getName();
		System.out.println("UserService 56↓ " + provider);
		System.out.println("String provider = principal.getName() : " + provider);
		
		// 사용자 정보를 DB에 저장
		// 1. model
		UserSNS user = new UserSNS();
		user.setName(name);
		user.setEmail(email);
		user.setProvider(provider);
		
		// 저장
		userRepository.save(user);
		
		model.addAttribute("name", name);
		model.addAttribute("email", email);
		
	}
}
