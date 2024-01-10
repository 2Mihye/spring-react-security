package com.kh.api.login.naver.dto;

import lombok.*;

@Builder
@Data
public class NaverDTO {
	private String id; // id를 long이냐 int로 줄 수 있지만, null값이 아니다 하면서 parse 하고 싶지 않다면 String으로 하는게 좋음. -> null값이라도 들어감.
	private String email;
	private String name;
}
