package com.kh.api.login.common;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//스프링 시큐리티 설정을 의미하는 어노테이션
//스프링에서 환경설정 파일임을 나타내는 어노테이션
@Configuration 
@EnableWebSecurity // 모든 URL이 스프링 시큐리티의 제어를 받도록 만든 어노테이션
public class SecurityConfig {
	
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
           
			
			//로그인을 하고난 후 로그인 세션을 유지하기 위한 설정
            .formLogin((formLogin) -> formLogin
                    .loginPage("/login")
                    .defaultSuccessUrl("/"))

            .logout((logout) -> logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true))
        ;
        return http.build();
    }
}