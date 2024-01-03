package com.kh.springChap3.service;

import com.kh.springChap3.model.MemberGoogle;

public interface MemberGoogleService {
	MemberGoogle findByUsername(String username);
	void saveMember(MemberGoogle user);
	
}
