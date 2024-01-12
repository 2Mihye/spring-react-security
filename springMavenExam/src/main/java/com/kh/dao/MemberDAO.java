package com.kh.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kh.vo.MemberVO;

@Mapper
public interface MemberDAO {// MemberMapper (전 수업 때는 DAO를 ~Mapper로 썼음)
	MemberVO loginMember(String memberId, String memberPwd);
}
