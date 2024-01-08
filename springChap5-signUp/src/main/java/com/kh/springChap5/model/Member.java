package com.kh.springChap5.model;

import java.util.Date;

import lombok.*;

@Getter @Setter
public class Member {
	private Long memberId;
	private String username;
	private String password;
	private String fullName;
	private String email;
	private Date registrationDate;
}
