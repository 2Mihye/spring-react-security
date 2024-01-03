package com.kh.springChap3googleAPI.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kh.springChap3googleAPI.model.UserGoogle;
import com.kh.springChap3googleAPI.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserGoogle user = userRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException("해당 이름으로 유저를 찾을 수 없습니다." + username));
		
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(),
				"",
				Collections.emptyList());
	}
}
