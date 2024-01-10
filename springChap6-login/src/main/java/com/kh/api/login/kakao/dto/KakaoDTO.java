package com.kh.api.login.kakao.dto;

import lombok.*;

@Builder
@Data
public class KakaoDTO {
	// 변수 : 동의항목
	private long id;
	private String email;
	private String nickname;
}
