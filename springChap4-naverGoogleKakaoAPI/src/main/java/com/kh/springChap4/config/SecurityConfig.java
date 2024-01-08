package com.kh.springChap4.config;

import java.util.Arrays;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	// CORS(Cross-Origin Resource Sharing) :  리소스를 서로 공유하는 것
	// Cors는 웹 브라우저에서 실행되는 자바스크립트가 다른 도메인에 접근할 수 있도록 해주는 보안 기술
	// 주로 웹에서나 API 서버에서 다른 도메인으로부터 HTTP 요청을 허용하도록 설정하는 데 사용한다.
	@Bean
	public CorsConfigurationSource corsConfigurationSource() { // CorsCorsConfigurationSource import는 cors.CorsConfigurationSource
		// CorsConfigurationSource 객체 생성
		CorsConfiguration configuration = new CorsConfiguration();
		// 허용할 Origin(주소) 목록 설정. 여기서는 http://localhost:3000만 허용해준 것
		// 만약 주소가 2가지 이상이라면 ,를 사용하여 주소를 추가할 수 있다.
		// configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:3001"));
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // 지금은 하나지만 여러개가 들어갈 수 있도록 Array 배열로 사용함.
		
		// 허용할 HTTP 메서드 목록 설정
		/*
		 configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "OPTIONS", "PATCH"));
		 "GET" : 데이터를 조회하기 위한 용도.
		 "POST" : 서버에 데이터를 제출하기 위한 용도.
		 "DELETE" : 서버에서 리소스(데이터)를 삭제하기 위한 용도. 
		 "PUT" : 서버에서 클라이언트가 수정한 데이터를 DB에 업데이트하기 위한 용도.
		 "OPTIONS" : 실제로 요청하기 전에 브라우저가 서버에게 해당 데이터에 대해 어떤 메서드와 헤더들을 사용할 수 있는지 확인하기 위한 용도. CORS에서 사전 검사를 요청하는 데 많이 사용.
		 "PATCH" : 데이터에 일부만 업데이트하기 위한 용도. PUT과 비슷하지만 전체 데이터를 업데이트하는 대신 일부만 업데이트할 때 사용.
		 */
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
		
		// Headers는 HTTP에서 허용할 목록 설정
		configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origins", "Content-Type", "Accept", "Authorization"));
		
		// 자격 증명 (cookie, 헤더 등)을 허용할지 여부 결정. 주로 로그인 상태를 유지하거나 사용자 관련 정보를 요청과 함께 전송할 때 많이 활용한다.
		configuration.setAllowCredentials(true);
		
		// UrlBasedCorsConfigurationSource : 객체 생성 등록. 경로별로 다른 CORS 구성을 관리할 수 있도록 도와줌.
		// 내가 경로를 설정하고 설정한 경로에만 Cors 설정을 동일하게 적용하겠다는 의미
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // 마지막 cors로 import
		source.registerCorsConfiguration("/**", configuration);
		
		// CORS 구성이 적용되도록 설정된 source를 반환 
		return source;
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			// 비활성화 .cors(cors -> cors.disable())
			/*
			 만약 CSRF를 사용하게 되면 SNS(Google, Kakao, Naver 등)에서 로그인을 위한 토큰과 CSRF에서 발급해주는 토큰 2개가 존재하는 것 = 한 번 더 인증한다는 것
			 .csrf(csrf -> cors.disable())
			 CSRF(Cross-Site Request Fogery)
			 공격을 방지하기 위한 방법 중 하나
			 CSRF라는 토큰이 있으며, 이 토큰은 사용자의 세션과 관련된 고유한 값으로 각 요청에 포함되어 있는지 토큰을 통해 확인하고 유효한지를 검증
			 사용자가 로그인할 때 마다 서버는 특별한 1회성 CSRF 토큰을 생성하고 이 토큰을 안전하게 쿠키에 저장.
			 웹 페이지의 폼이나 Ajax 요청에서 토큰을 포함시켜야 함. 보통은 input에서 hidden 필더나 헤더에 토큰을 넣어서 전송하고 1회성이기 때문에 DB에는 저장하지 않는다.
			 토큰이 일치하지 않으면 해당 요청은 거부되거나 무시된다.			 
			 */
			.authorizeHttpRequests(authorizeHttpRequests ->
				authorizeHttpRequests
				.requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
			.oauth2Login(oauth2Login ->
			oauth2Login
					.successHandler(new SimpleUrlAuthenticationSuccessHandler("/loginSuccess")));
		return http.build();
	}
	
	// 추후 네이버 등록한 정보(naverClientRegistration()) 저장하기 위한 공간(InMemoryClientRegistrationRepository) 생성
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(
        		googleClientRegistration(),
        		kakaoClientRegistration(),
                naverClientRegistration() 
                );
    }
	
	// 인증 통합 관리하는 매니저
	@Bean
	public OAuth2AuthorizedClientManager authorizedClientManager(
			ClientRegistrationRepository clientRegistrationRepository,
			OAuth2AuthorizedClientRepository authorizedClientRepository) {
		// 클라이언트 인증 처리
		OAuth2AuthorizedClientProvider authorizedClientProvider = 
				OAuth2AuthorizedClientProviderBuilder.builder()
				.authorizationCode()
				.build();
		// 클라이언트 등록 정보와 인증된 클라이언트 저장소를 설정
		DefaultOAuth2AuthorizedClientManager authorizedClientManager = new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientRepository);
		
		authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
		
		return authorizedClientManager;
	}
	
	
	// 네이버 클라이언트의 등록 정보를 생성하는 메서드로 클라이언트 아이디와 시크릿, 인증 후 리다이렉트 URI 설정
	public ClientRegistration naverClientRegistration() {
		return ClientRegistration.withRegistrationId("naver")
				.clientId("tmfesNnXtMZzYFKN7Xuu") // .clientId("https://developers.naver.com/apps/#/myapps/ 안에 적혀있는 Client ID 가져오기")
				.clientSecret("eEfdEaY3rJ") // .clientId("https://developers.naver.com/apps/#/myapps/ 안에 적혀있는 Client Secret 가져오기")
				.redirectUri("http://localhost:8080/login/oauth2/code/naver") // 네이버에서도 인증 후 OAuth2 엔드포인트설정
				.clientName("Naver")
				.authorizationUri("https://nid.naver.com/oauth2.0/authorize")
				.tokenUri("https://nid.naver.com/oauth2.0/token")
				.userInfoUri("https://openapi.naver.com/v1/nid/me")
				.userNameAttributeName("response")
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.build();
	}
	
	
	// 구글 클라이언트의 등록 정보를 생성하는 메서드로 클라이언트 아이디와 시크릿, 인증 후 리다이렉트 URI 설정
	public ClientRegistration googleClientRegistration() {
		return ClientRegistration.withRegistrationId("google")
				.clientId("664155680404-ohm9nk0kococihre5o6gi1vv428r9fde.apps.googleusercontent.com")
				.clientSecret("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")
				.redirectUri("http://localhost:8080/login/oauth2/code/google") // 네이버에서도 인증 후 OAuth2 엔드포인트설정
				.clientName("Google")
				.authorizationUri("https://accounts.google.com/o/oauth2/auth")
				.tokenUri("https://www.googleapis.com/oauth2/v4/token")
				.userInfoUri("https://www.googleapis.com.oauth2/v3/userinfo")
				.userNameAttributeName("sub")
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.scope("openid", "profile", "email")
				.jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
				.build();
	}
	
	
	// 카카오 클라이언트의 등록 정보를 생성하는 메서드로 클라이언트 아이디와 시크릿, 인증 후 리다이렉트 URI 설정
	public ClientRegistration kakaoClientRegistration() {
		return ClientRegistration.withRegistrationId("kakao")
				.clientId("6c3b346d2a0b7b41c957f43c7b5f73e0")
				.clientSecret("baaXUhOwcQ9uw7dnRDF91IM0KrJgYaKo")
				.redirectUri("http://localhost:8080/login/oauth2/code/kakao") // 네이버에서도 인증 후 OAuth2 엔드포인트설정
				.clientName("Kakao")
				.authorizationUri("https://kauth.kakao.com/oauth/authorize")
				.tokenUri("https://kauth.kakao.com/oauth/token")
				.userInfoUri("https://kapi.kakao.com/v2/user/me")
				.userNameAttributeName("id")
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.scope("profile_nickname", "account_email") // 추후 변경할 동의항목 내역
				.build();
	}
}
