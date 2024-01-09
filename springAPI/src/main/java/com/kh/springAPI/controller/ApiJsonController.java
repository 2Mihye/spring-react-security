package com.kh.springAPI.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiJsonController{
	@GetMapping(value = "/api/get", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String DBInsert() {
		// 데이터를 시작하기 전에는 StringBuilder
		StringBuilder result = new StringBuilder();
		try {
			String apiUrl="http://apis.data.go.kr/1360000/MidFcstInfoService/getMidFcst";
			String apiKey="TZ0Gee1Znhc+yA77aXhM+a9KA1uTiBp1hhH4rLT6HkuhHbAO0L/cf8kIX+eW6nSVpnK2i3on5hABW5fy1BjQtQ=="; // 변하지 않는 값
			String pageNo = "1";
			String numOfRows = "10";
            String dataType = "xml";
            String stnId = "108";
            String tmFc = "202401090600";
            String encodedApiKey = URLEncoder.encode(apiKey, "UTF-8");
            String encodedUrl = String.format("%s?serviceKey=%s&pageNo=%s&numOfRows=%s&type=%s&stnId=%s&tmFc=%s",
                    apiUrl, encodedApiKey, pageNo, numOfRows, dataType, stnId, tmFc);
			
			URL url = new URL(encodedUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			// 화면에 데이터가 출력될 수 있도록 readLine을 써서 출려 
			String line;
			while((line = reader.readLine()) != null) {
				result.append(line);
			}
			reader.close();
			connection.disconnect();
			
			// 데이터베이스에 저장하는 메서드 실행
			//insertIntoOracleDB(result.toString());
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// return result.toString();
		
		// json 파일로 변환하여 보여주기
		String jsonResult = convertXmlToJson(result.toString());
		return jsonResult;
	}
	
	// xml로 보이는 파일을 json 형식으로 변환하여 화면에 출력하는 메서드
	private String convertXmlToJson(String xmlData) {
		JSONObject jsonObj = XML.toJSONObject(xmlData);
		return jsonObj.toString();
	}
	
}

