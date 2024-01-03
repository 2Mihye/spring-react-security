package com.kh.springChap3.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.*;

@Getter @Setter
public class MemberGoogle {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="mg_seq")
	@SequenceGenerator(name="mg_seq", sequenceName="mg_seq", allocationSize=1)
	private Long id; // 기본키
	private String username;
	private String email;
}
