package com.kh.springAPI.controller;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ApiController {
	/*
	
	
	// Java로 api 값이 잘 들어오는지 확인하기 위해 만들어 실행하고 연결은 Spring으로 진행한다.
	@GetMapping("/api/get")
	public String getApiData() {
		
		StringBuilder result = new StringBuilder();
		
		// UTF-8로 가져와서 추가
		try {
			String apiUrl = "http://시작하는 api 주소 작성";
			String apiKey = "api 키";
			String numOfRows= ""; // api 파일에 있는 값을 그대로 쓴다.
			String pageNo = "";
			String responseType = "";
			String dissCd = "";
			String znCd = "";
			
			String encodedApiKey = URLEncoder.encode(apiKey, "UTF-8");
			String encodedUrl = String.format("$s?apiKey=%s&numOfRows=%s&pageNo=%s&type=%s&dissCd=%s&znCd=%s",
					 apiUrl, encodedApiKey, numOfRows, pageNo, responseType, dissCd, znCd);
			
			
			// HTTP 연결
			URL url = new URL(encodedUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			
			// 주소 연결이 되면 주소에 작성된 값 가져오기 위해 RequestMethod("GET") 요청
			connection.setRequestMethod("GET");
			
			// 응답에 대한 값 전달
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while((line =  reader.readLine()) != null) {
				result.append(line);
			}
			reader.close();
			connection.disconnect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result.toString();
	}
	
	*/
}
