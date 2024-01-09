package com.kh.springAPI.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiDBInsertController {
	@GetMapping("/map_info/add")
	public String DBInsert() {
		// 데이터를 시작하기 전에는 StringBuilder
		StringBuilder result = new StringBuilder();
		try {
			String apiUrl="http://apis.data.go.kr/1360000/MidFcstInfoService/getMidFcst";
			String apiKey="TZ0Gee1Znhc+yA77aXhM+a9KA1uTiBp1hhH4rLT6HkuhHbAO0L/cf8kIX+eW6nSVpnK2i3on5hABW5fy1BjQtQ=="; // 변하지 않는 값
			String pageNo = "2";
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
			insertIntoOracleDB(result.toString());
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	private void insertIntoOracleDB(String data) {
		String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE";
		String username = "khbank";
		String password = "KHBANK";
		
		try(Connection connection = DriverManager.getConnection(jdbcUrl, username, password)){ // connection import는 무조건 sql로 !
			String sql = "INSERT INTO culture (id, data) VALUES(culture_seq.NEXTVAL,?)";
			try(PreparedStatement statement = connection.prepareStatement(sql)){
				statement.setString(1, data);
				statement.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
