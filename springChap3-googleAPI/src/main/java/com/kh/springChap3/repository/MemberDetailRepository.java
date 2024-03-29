package com.kh.springChap3.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.springChap3.model.UserGoogle;

public interface MemberDetailRepository extends JpaRepository <UserGoogle, Long> { // 인증용
	// 추가적으로 필요한 메서드 작성
	Optional<UserGoogle> findByUsername(String username);
}
