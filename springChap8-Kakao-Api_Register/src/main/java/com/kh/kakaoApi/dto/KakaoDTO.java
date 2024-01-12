package com.kh.kakaoApi.dto;


import lombok.*;

@Builder
@Data
public class KakaoDTO {
	// 변수 : 동의항목
	private long id;
	private String email;
	private String nickname;
	// add
	private String name;
	private String birthdate;
}