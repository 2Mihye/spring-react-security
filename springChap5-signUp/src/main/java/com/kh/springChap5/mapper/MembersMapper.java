package com.kh.springChap5.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.springChap5.model.Member;

@Mapper
public interface MembersMapper {
	void insertMember(Member member);
	void updateMember(Member member);
	List<Member> getAllMembers();
}