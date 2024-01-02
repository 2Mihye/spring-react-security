package com.kh.springChap1.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="member_seq")
	@SequenceGenerator(name="member_seq", sequenceName="member_seq", allocationSize=1)
	private Long id;
	private String memberName;
	private String email;
}
