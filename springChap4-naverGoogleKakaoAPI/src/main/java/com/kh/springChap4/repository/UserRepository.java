package com.kh.springChap4.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.springChap4.model.UserSNS;

public interface UserRepository extends JpaRepository<UserSNS, Long>{
	
}
