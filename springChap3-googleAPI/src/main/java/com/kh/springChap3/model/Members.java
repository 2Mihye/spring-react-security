package com.kh.springChap3.model;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@Entity
public class Members {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="members_seq")
	@SequenceGenerator(name="members_seq", sequenceName="members_seq", allocationSize=1)
	private Long id; // 기본키
	private String name;
	private String email;
	
	public Members() {
		
	}
	
	public Members(String name, String email) {
		this.name = name;
		this.email = email;
	}
	
}
