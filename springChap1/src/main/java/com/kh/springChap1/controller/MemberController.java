package com.kh.springChap1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kh.springChap1.model.Member;
import com.kh.springChap1.repository.MemberRepository;

@RestController
@RequestMapping("/api/member")
@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
public class MemberController {
	@Autowired
	private MemberRepository memberRepository;
	
	@GetMapping // ("/api/member") GetMapping 뒤에 아무것도 없으면 위에서 작성해준 ("/api/member")로 자동으로 지정\
	public List<Member> getAllUsers(){
		return memberRepository.findAll();
	}
	
	/*
	 @PostMapping : 클라이언트(사용자)가 html에 작성한 정보를 DB에 저장할 수 있도록 도와주는 어노테이션
	 ResponseEntity : 응답을 나타내는 클래스로 우리가 자주봤던 404, 400, 500(에러) 이외에 200이라는 응답이 있는데, 이는 성공적으로 데이터를 전송했다는 의미.
	 				  ResponseEntity.ok 라는 것은 데이터를 잘 전송했다는 의미이므로 ok가 200이라는 내용을 담고있음.
	 */
	@PostMapping
	public ResponseEntity<Member> createMember (@RequestBody Member newMember) {
		Member createdMember = memberRepository.save(newMember);
		return ResponseEntity.ok(createdMember);
	}
}
