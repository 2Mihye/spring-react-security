package com.kh.springAPI.controller;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.nio.file.Path;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/csv-data")
public class ApiDBController {
	/*
	 produces = MediaType.데이터타입 지정 : 가져올 데이터 타입을 지정해주는 메서드
	 								   MediaType 해준다음 텍스트 파일을 가져올 것인지 다른 파일을 가져올 것인지 작성
	 								   Text/plain 미디어 타입을 생성하겠다는 표시를 해준 것으로 텍스트 형식의 응답을 생성
	 */
	@GetMapping(produces = MediaType.TEXT_PLAIN_VALUE) // MediaType은 springframework.http로 import
	public ResponseEntity<InputStreamResource> csvData() {
		try {
			
			String csvFileName = "cultureMap.csv";
			Path csvFilePath = Paths.get(ApiDBController.class.getClassLoader().getResource(csvFileName).toURI());
			
			/*
			InputStreamResource : Spring 에서 제공하는 클래스로 spring의 resource로 감싸는 역할을 해서 파일이나 다른 소스로부터 읽어온 데이터를 Spring에서 관리되는 소스로 사용할 수 있게 해준다.
						예를 들어 파일을 가져온 다음 파일을 Spring으로 읽어온다면 File file = new File("exam.txt");
						InputStream inputStream = new FileInpuStream(file);
						InputStreamResource resource = new InputStreamResource(inputStream);
						
						파일을 inputStreamResource를 사용하여 Spring 형식으로 변환하는 과정
			*/
			InputStreamResource resource = new InputStreamResource(new FileInputStream(csvFilePath.toFile()));
			
			/* 
			 Http 응답 헤더 설정
			 CONTENT_DISPOSITON : 파일을 어떻게 처리할지 브라우저에게 알려주는 역할
			 inline : 브라우저가 해당 파일을 표시할 수 있는 경우에만 화면에 표시할 수 있도록 지정하여 에러를 최소화
			 filename : 브라우저가 들어왔을 때 파일 다운로드할 때 이름 지정
			 */
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + csvFileName);
			
			return ResponseEntity.ok().headers(headers).body(resource);
		
		} catch(Exception e) {
			
			e.printStackTrace();
			return ResponseEntity.badRequest().body(null);
		}
	}
}
