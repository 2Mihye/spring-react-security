package com.kh.springChap4.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.springChap4.model.UserSNS;
import com.kh.springChap4.repository.UserRepository;

@Service
public class UserService {	
		// 2. repository
		private final UserRepository userRepository;
		
		@Autowired // Autowired나 선언하는 녀석들은 항상 위에 있도록 해줘야 함 !
		public UserService(UserRepository userRepository){
			this.userRepository = userRepository;
		}
		
		public void naverLoginService(OAuth2User principal, String naverResponse, Model model) {
		
		System.out.println("OAuth2User Attributes : " + principal.getAttributes());
		String name = null;
		String email = null;
		
		// 만약 naver 응답이 들어와서 null 값이 아니라면 !
		if(naverResponse != null) {
			// 들어온 naver응답 값을 Json 형식으로 담을 수 있도록 아무런 값도 없는 Json형태를 세팅해주고 그 안에 Mapper 처리 한다.
			JsonNode responseNode;
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				responseNode = objectMapper.readTree(naverResponse).get("response");
				
				if(responseNode != null) {
					name = responseNode.get("name").asText();
					email = responseNode.get("email").asText();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
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
		System.out.println("UserService 68↓ " + provider);
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
		
		// model이 naverResponse로 가져와야하는 경우 naver 응답 추가
		model.addAttribute("naverResponse", naverResponse);
	}
}
