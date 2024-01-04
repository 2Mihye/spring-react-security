package com.kh.springChap3.service;

import com.kh.springChap3.model.UserGoogle;

public interface UserService {
	UserGoogle findByUsername(String username);
	void saveUser(UserGoogle user);
}

