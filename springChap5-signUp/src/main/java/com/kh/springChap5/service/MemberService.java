package com.kh.springChap5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.springChap5.mapper.MembersMapper;
import com.kh.springChap5.model.Member;

@Service
public class MemberService {
	@Autowired
	private MembersMapper membersMapper;
	
	// 회원 정보 저장하기
	public void signUpMember(Member member) {
		membersMapper.insertMember(member);
	}
}