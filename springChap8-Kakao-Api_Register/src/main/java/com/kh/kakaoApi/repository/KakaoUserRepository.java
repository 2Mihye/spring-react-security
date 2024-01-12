package com.kh.kakaoApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.kakaoApi.vo.KakaoUser;

public interface KakaoUserRepository extends JpaRepository<KakaoUser, Long> {
	
}
