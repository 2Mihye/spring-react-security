package com.kh.springChap3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.springChap3.model.UserGoogle;

public interface UserGoogleRepository extends JpaRepository<UserGoogle, Long> {
	UserGoogle findByUsername(String username);
}
