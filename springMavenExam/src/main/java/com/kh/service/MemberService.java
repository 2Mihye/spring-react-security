package com.kh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.dao.MemberDAO;
import com.kh.vo.MemberVO;

@Service
public class MemberService {
	@Autowired
	private MemberDAO memberDao;
	// VO 참조하여 로그인할 사용자 함수 작성
	public MemberVO loginMember(String memberId, String memberPwd) {
		return memberDao.loginMember(memberId, memberPwd);
	}
	
	/*
	 * public void loginMember(MemberVO memberVO){
	 * 		memberDao.loginMember(memberVO);
	 * }
	 */
}
