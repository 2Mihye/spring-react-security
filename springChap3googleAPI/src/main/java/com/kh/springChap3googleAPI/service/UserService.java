package com.kh.springChap3googleAPI.service;

import com.kh.springChap3googleAPI.model.UserGoogle;

public interface UserService {
	UserGoogle findByUsername(String username);
	void saveUser(UserGoogle user);
}
