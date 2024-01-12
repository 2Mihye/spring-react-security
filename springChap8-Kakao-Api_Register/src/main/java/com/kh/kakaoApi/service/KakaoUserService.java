package com.kh.kakaoApi.service;

import org.springframework.stereotype.Service;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.kh.kakaoApi.dto.KakaoDTO;
import com.kh.kakaoApi.repository.KakaoUserRepository;
import com.kh.kakaoApi.vo.KakaoUser;


@Service
public class KakaoUserService {

		// Value를 썼기 때문에 각 값을 변수에 넣어 보관하겠다는 의미
		@Value("${kakao.client.id}") // Spring으로 import
		private String KAKAO_CLIENT_ID;
		
		@Value("${kakao.client.secret}")
		private String KAKAO_CLIENT_SECRET;	
		@Value("${kakao.redirect.url}")
		private String KAKAO_REDIRECT_URL;
		
		// 카카오 자체에서 인증으로 들어가는 공식 주소
		private final static String KAKAO_AUTH_URI ="https://kauth.kakao.com";

	    private final static String KAKAO_API_URI="https://kapi.kakao.com";
		
		private final KakaoUserRepository kakaoUserRepository;
		
	    public KakaoUserService(KakaoUserRepository userRepository) {
	        this.kakaoUserRepository = userRepository;
	    }
		
		public String getKakaoLogin() {
			return KAKAO_AUTH_URI + "/oauth/authorize?client_id=" + KAKAO_CLIENT_ID + "&redirect_uri=" + KAKAO_REDIRECT_URL + "&response_type=code"; // 카카오 개발자 공식문서에 적혀있는 주소임
		}
		
		public KakaoDTO getKakaoInfo(String code, String name, String birthdate) throws Exception {
		        
			if(code == null) throw new Exception("존재하는 인증코드가 없습니다.");
			
			// 로그인이 허용된 토큰이 들어갈 공간
			String accessToken="";

			try {
				HttpHeaders headers = new HttpHeaders(); // Spring으로 import
				headers.add("Content-type", "application/x-www-form-urlencoded");
				MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
				params.add("grant_type", "authorization_code");
				params.add("client_id", KAKAO_CLIENT_ID);
				params.add("client_secret", KAKAO_CLIENT_SECRET);
				params.add("code", code);
				params.add("redirect_uri", KAKAO_REDIRECT_URL);
				

				RestTemplate restTemplate = new RestTemplate();
	            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

				ResponseEntity<String> response = restTemplate.exchange(KAKAO_AUTH_URI + "/oauth/token", HttpMethod.POST, httpEntity, String.class);

				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObject = (JSONObject) jsonParser.parse(response.getBody());
				System.out.println("jsonObject" + jsonObject);
				accessToken = (String) jsonObject.get("access_token");
				System.out.println("accessToken" + accessToken);

			} catch(Exception e) {
				throw new Exception("api를 불러오지 못했습니다.");
			}
			
			return getUserInfoWithToken(accessToken, name, birthdate);
		}
		
		// 회사에서 카카오로부터 로그인 할 수 있도록 허용받은 받은 로그인 허용 토큰을 사용하여 카카오 API에서 사용자 정보를 가져오는 메서드 (개인 사용자가 아님)
		private KakaoDTO getUserInfoWithToken(String accessToken, String name, String birthdate) throws Exception {
			// 토큰용 HTTPHeader 생성
			HttpHeaders headers = new HttpHeaders();
			// Bearer : Http 요청에서 인증할 때 특정 형태로 변환하여 토큰 타입을 나타내는 것
			headers.add("Authorization", "Bearer " + accessToken);
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
					.nickname(nickname)
					// 이름과 생년월일 추가
					.name(name)
					.birthdate(birthdate)
					.build();
		}
	
		// 데이터베이스에 저장하는 메서드 생성
		public KakaoUser registerUser(KakaoDTO kakaoDTO) {
			KakaoUser user = new KakaoUser();
			user.setEmail(kakaoDTO.getEmail());
			user.setNickname(kakaoDTO.getNickname());
			user.setName(kakaoDTO.getName());
			user.setBirthdate(kakaoDTO.getBirthdate());
			
			// 사용자를 데이터베이스에 저장
			return kakaoUserRepository.save(user);
		}
	
}
