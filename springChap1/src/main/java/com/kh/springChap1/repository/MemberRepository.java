package com.kh.springChap1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.springChap1.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	// 추가적으로 필요한 메서드만 작성

}
