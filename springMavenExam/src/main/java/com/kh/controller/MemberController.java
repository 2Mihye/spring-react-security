package com.kh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.kh.service.MemberService;
import com.kh.vo.MemberVO;

@Controller
@RequestMapping("/")
public class MemberController {
	private MemberService memberService;
	
	@PostMapping("/login")
	public String memberLogin(String memberId, String memberPwd) {
		// 로그인할 때 필요한 코드 작성
		memberService.loginMember(memberId, memberPwd);
		
		return "redirect:/";
	}
}
