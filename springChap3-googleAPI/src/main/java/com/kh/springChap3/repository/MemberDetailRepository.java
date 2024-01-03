package com.kh.springChap3.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.springChap3.model.MemberGoogle;

public interface MemberDetailRepository extends JpaRepository <MemberGoogle, Long> { // 인증용
	// 추가적으로 필요한 메서드 작성
	Optional<MemberGoogle> findByUsername(String username);
}
