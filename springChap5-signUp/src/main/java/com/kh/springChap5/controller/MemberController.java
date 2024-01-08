package com.kh.springChap5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.kh.springChap5.model.Member;
import com.kh.springChap5.service.MemberService;

@Controller
@RequestMapping("/members")
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("members", new Member());
		return "register";
	}
	
	@PostMapping("/register")
	public String registerMember(Member member) {
		memberService.signUpMember(member);
		return "redirect:/";
	}
	
	/*
	@PostMapping("/login")
	public String loginMember(Member member) {
	
	}*/
	
	@GetMapping("/list")
	public String showMemberList(Model model) {
		List<Member> members = memberService.getAllMembers();
		model.addAttribute("members", members);
		return "list";
	}
	
	
}
