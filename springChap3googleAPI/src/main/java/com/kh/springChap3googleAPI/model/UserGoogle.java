package com.kh.springChap3googleAPI.model;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@Entity
public class UserGoogle {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="usrgoogle_seq")
	@SequenceGenerator(name="usrgoogle_seq", sequenceName="usrgoogle_seq", allocationSize=1)
	private Long id;
	
	private String username;
	private String email;
}
