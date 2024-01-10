package com.kh.api.login.kakao.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.kh.api.login.kakao.dto.KakaoDTO;

@Service
public class KakaoService {
	// Value를 썼기 때문에 각 값을 변수에 넣어 보관하겠다는 의미
	@Value("${kakao.client.id}") // Spring으로 import
	private String KAKAO_CLIENT_ID;
	
	@Value("${kakao.client.secret}")
	private String KAKAO_CLIENT_SECRET;	
	@Value("${kakao.redirect.url}")
	private String KAKAO_REDIRECT_URL;
	
	// 카카오 자체에서 인증으로 들어가는 공식 주소
	private final static String KAKAO_AUTH_URI="http://kauth.kakao.com"; // URI가 URL보다 더 큰 범위로 URL은 URI에 들어가 있는 링크일 뿐임.
	// 카카오 자체에서 API로 들어가는 공식 주소
	private final static String KAKAO_API_URI="https://kapi.kakao.com";
	
	public String getKakaoLogin() {
		return KAKAO_AUTH_URI + "/oauth/authorize?client_id=" + KAKAO_CLIENT_ID + "&redirect_uri=" + KAKAO_REDIRECT_URL + "&response_type=code"; // 카카오 개발자 공식문서에 적혀있는 주소임
	}
	
	public KakaoDTO getKakaoInfo(String code) throws Exception {
		if(code == null) throw new Exception("존재하는 인증코드가 없습니다.");
		
		// 로그인이 허용된 토큰이 들어갈 공간
		String accessToken="";
		
		// 만약 토큰이 재발급 된다면 재발급된 토큰이 들어갈 공간
		String refreshToken="";
		
		// http Header에 내 정보 흘려보내기
		try {
			HttpHeaders headers = new HttpHeaders(); // Spring으로 import
			headers.add("Content-type","application/x-www-form-urlencoded");
			
			// 카카오 로그인 아이디 + 시크릿키  + 코드 + 리다이렉트 url을 모두 붙이기 
			/* MultiValueMap : Spring에서 제공하는 인터페이스로 여러 개의 값을 하나의 키에 연결할 수 있도록 합쳐주는 역할을 하며 http에서 요청이 여러 개일 때 자주 사용한다.
			 * <String, String>인 이유는 key, value를 각각 String으로 넣을 것이기 때문이다.
			 * MultiValueMap<key, value> key와 value를 String으로 설정해서 카카오톡에서 설정한 값을 적고 그 값에 대한 내용물을 가져오겠다는 의미
			 * EX) MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			 * 		param.add("key1", "value1");
			 * 		param.add("key1", "value2");
			 * 		param.add("key2", "value3");
			 * 		결과 key1 -> [value1, value2]
			 * 			key2 -> [value3]
			 */
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			params.add("grant_type", "authorization_code");
			params.add("client_id", KAKAO_CLIENT_ID);
			params.add("Client_secret", KAKAO_CLIENT_SECRET);
			params.add("code", code);
			params.add("redirect_uri", KAKAO_REDIRECT_URL);
			
			// Spring에서 제공하는 것
			// RestTemplate을 사용하여 HTTP에 요청을 보내고 요청에 대한 응답을 받아오는 Template
			// HTTP 요청을 생성하고 서버에 전달해주는 역할
			RestTemplate restTemplate = new RestTemplate();
			
			// HTTP 요청이나 응답의 헤더나 본문 http 메서드를 포함하는 Entity를 나타냄
			HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
			
			// 요청보낼 URI
			// 카카오 OAuth 토큰을 얻기 위해 /oauth/token POST 요청을 보냄
			// httpEntity : 본문에서 요청하고자 하는 내용과 헤더 정보를 포함하는 객체
			// String.class : 서버에서 오는 응답을 String 형태로 받아오겠다는 의미
			ResponseEntity<String> response = restTemplate.exchange(KAKAO_AUTH_URI + "/oauth/token", HttpMethod.POST, httpEntity, String.class);
			
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(response.getBody());
			
			accessToken = (String) jsonObject.get("access_token");
			refreshToken = (String) jsonObject.get("refresh_token");
		} catch(Exception e) {
			throw new Exception("api를 불러오지 못했습니다.");
		}
		
		return getUserInfoWithToken(accessToken);
	}
	
	// 회사에서 카카오로부터 로그인 할 수 있도록 허용받은 받은 로그인 허용 토큰을 사용하여 카카오 API에서 사용자 정보를 가져오는 메서드 (개인 사용자가 아님)
	private KakaoDTO getUserInfoWithToken(String accessToken) throws Exception {
		// 토큰용 HTTPHeader 생성
		HttpHeaders headers = new HttpHeaders();
		// Bearer : Http 요청에서 인증할 때 특정 형태로 변환하여 토큰 타입을 나타내는 것
		headers.add("Authorization", "Bearer" + accessToken);
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
		
		// 내용을 담아놓을 template 생성
		RestTemplate rt = new RestTemplate();
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
		
		ResponseEntity<String> response = rt.exchange(KAKAO_API_URI + "/v2/user/me", HttpMethod.POST, httpEntity, String.class);
		
		// Response 데이터 가져오기
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject =(JSONObject) jsonParser.parse(response.getBody());
		JSONObject account = (JSONObject) jsonObject.get("kakao_account");
		JSONObject profile = (JSONObject) account.get("profile");
		
		long id = (long) jsonObject.get("id");
		String email = String.valueOf(account.get("email"));
		String nickname = String.valueOf(profile.get("nickname"));
		
		return KakaoDTO.builder()
				.id(id)
				.email(email)
				.nickname(nickname).build();
		
	}
}
