package com.kh.springChap3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.springChap3.model.MemberGoogle;

public interface MemberGoogleRepository extends JpaRepository<MemberGoogle, Long> { // 로그인용
	// 추가적으로 필요한 메서드 작성
	MemberGoogle findByUsername(String username);
}
