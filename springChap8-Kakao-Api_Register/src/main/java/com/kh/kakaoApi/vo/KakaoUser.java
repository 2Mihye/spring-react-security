package com.kh.kakaoApi.vo;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
public class KakaoUser {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="kakaoUser_seq")
	private Long id;
	private String email;
	private String nickname;
	private String name;
	private String birthdate; // String으로 하는 이유는 수정하지 못하게 막기 위해서임.
}
