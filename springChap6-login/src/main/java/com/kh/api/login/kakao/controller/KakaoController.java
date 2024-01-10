package com.kh.api.login.kakao.controller;

import com.kh.api.login.common.MsgEntity;
import com.kh.api.login.kakao.dto.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.kh.api.login.kakao.service.KakaoService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("kakao")
public class KakaoController {
	private final KakaoService kakaoService;
	
	@GetMapping("/callback")
	// MsgEntity : 결과에 대한 내용을 담을 Entity
	public ResponseEntity<MsgEntity> callback(HttpServletRequest request) throws Exception {
		KakaoDTO kakaoInfo = kakaoService.getKakaoInfo(request.getParameter("code"));
		return ResponseEntity.ok()
				.body(new MsgEntity("Success", kakaoInfo));
	}
}
