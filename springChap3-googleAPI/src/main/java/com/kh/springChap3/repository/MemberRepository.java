package com.kh.springChap3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.springChap3.model.Members;

public interface MemberRepository extends JpaRepository <Members, Long> {
	// 추가적으로 필요한 메서드 작성
	
}
