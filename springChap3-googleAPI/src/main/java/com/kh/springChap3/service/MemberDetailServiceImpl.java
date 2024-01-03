package com.kh.springChap3.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kh.springChap3.model.MemberGoogle;
import com.kh.springChap3.repository.MemberGoogleRepository;

@Service
public class MemberDetailServiceImpl implements UserDetailsService{
	@Autowired
	private MemberGoogleRepository memberGoogleRepository;
	
	// Spring Security에서 지어준 이름이기 때문에 Service 부분에서 User로 바꾸지 않고 사용하는 것이 개발자들간의 약속임.
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MemberGoogle user = memberGoogleRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("일치하는 유저 정보를 찾을 수 없습니다." + username));
		return new org.springframework.security.userdetails.User(user.getUsername(), "", Collections.emptyList()); // 이렇게 쓰는 이유는 Security에서 사용하는 user와 충돌이 나기 때문에
		/*
		 * return new org.springframework.security.userdetails.User(user.getUsername(), "", Collections.emptyList());
		 * "" : // 비밀번호를 OAuth인증에서는 사용하지 않음. // {noop}은 ""와 같음.
		 */
	}
}
