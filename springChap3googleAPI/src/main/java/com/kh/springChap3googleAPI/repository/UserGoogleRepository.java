package com.kh.springChap3googleAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.springChap3googleAPI.model.UserGoogle;

public interface UserGoogleRepository extends JpaRepository<UserGoogle, Long> {
	UserGoogle findByUsername(String username);
}
