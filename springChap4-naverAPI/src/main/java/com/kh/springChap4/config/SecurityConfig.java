package com.kh.springChap4.config;

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

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
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
