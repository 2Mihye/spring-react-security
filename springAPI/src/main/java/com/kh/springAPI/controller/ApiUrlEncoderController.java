package com.kh.springAPI.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.web.bind.annotation.*;

@RestController
public class ApiUrlEncoderController {
	
	@GetMapping("/api/encoder/data")
	public String allowBasic() {
		StringBuffer result = new StringBuffer();
		
		try {
			StringBuilder urlBuilder = new StringBuilder("api Url을 넣어준다.");
			urlBuilder.append("?ServiceKey=발급받은 서비스키");
			urlBuilder.append("&pageNo= 값 적어주기");
			urlBuilder.append("&numOfRows= 값 적어주기");
			urlBuilder.append("&type=json"); // 결과로 보여줄 포맷으로 xml로 보여주고 싶다면 xml로 설저 
			
			URL url = new URL(urlBuilder.toString());
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			BufferedReader rd;
			// 만약 response 코드가 200보다 크거나 300보다 작을 때
			// Http 응답코드 :  200~299 사이는 성공! 300~399 사이는 재전송! 
			if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			} else {
				// 실패했을 경우
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			
			// 글자를 읽겠다는 녀석
			String line;
			while((line = rd.readLine()) != null) {
				result.append(line).append("\n"); // enter치고 보는 것
			}
			rd.close();
			conn.disconnect();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result.toString();
	}
}
