package com.kh.xmlApi.controller;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// api 가져온 다는 것은 남이 만들어준 것을 편하게 주소로만 가져온다는 것. 주소값 같은 경우 상대방이 결제하라고 막아놓으면 끝 !


// Copy files 는 파일이 가볍거나 수정해야할 때 // Link file은 대용량이거나 할 때
// 최종적으로 return하는 종류가 html = @Controller
// 						 백엔드 api 주소 = @RestController

// xml로 된 파일을 가져왔을 때 사용하는 방법
@RestController
public class ApiXmlController {
	
	// 메서드를 작성 했다면 메서드를 최종적으로 반환return하는 값도 같아야 한다.
	@GetMapping("/api_explorer")
	public ResponseEntity xmlApi() {
		// 파일을 가져올 때 항상 String Builder
		// 파일 내용이 들어갈 Builder을 미리 세팅해서 비어있는 Builder 안에 가져온 내용을 채울 예정
		StringBuilder result = new StringBuilder();
		
		// 1.apiUrl 2.Kye 3.xml 형식으로 가져올 것! <만약 xml 파일이 url 주소값으로 가지고 오는 것이 아니라 파일 자체를 다운받아 가지고 있다면 1번과 2번 값은 넣어줄 의무가 없다.>
		// 파일의 위치와 파일을 변환해주는 작업을 해주면 된다. 
		// 파일을 가져올 땐 Buffer와 Stream을 사용한다.
		// 최종적으로 json 형식으로 xml 파일을 변화하여 출력
		// key value 이기 때문에 List 사용 -> 추후 return 데이터 목록
		
		List<Map<String, Object>> mapList =
new ArrayList<Map<String, Object>>(); // 위 MapList에서 받아오는 형식과 =을 써서 가져오는 형식이 같아야 한다 !
		
		// return data key 목록을 담을 공간 만들기
		List<String> headerList = new ArrayList<String>();
		
		try {
			// 파일을 읽어올 땐 BufferedReader을 가져와야한다.
			BufferedReader br = Files.newBufferedReader(Paths.get("isgi.d_0001.xml"));
			String line = "";
			
			// ★★★★★ readLine() : 한 줄씩 읽어서 문자열을 return해주는 메서드
			// ★★★★★ split() : 입력받은 형식을 정규 표현식이나 특정 문자를 기준으로 문자열을 나누어 배열에 저장하여 return
			while((line = br.readLine()) != null) {
				// 받아올 정보들 넣어주기
				List<String> stringList = new ArrayList<String>();
				String stringArray[] = line.split("xml 형식에 맞춰서 다듬기"); // .split("</fornt>") 를 넣으면 </front>를 기준으로 잘림
				stringList = Arrays.asList(stringArray);
				
				// 맨 위가 어디인지 확인 후 header 인식
				// 꼭대기를 찾아주고 나서 데이터를 변환해서 json 형식으로 출력
				if(headerList.size() == 0) {
					headerList = stringList;
				} else {
					Map<String, Object> map = new HashMap<String, Object>(); // 담아주는 값과 객체를 만들어주는 값이 동일하게 설정
					for(int i = 0; i < headerList.size(); i++) {
						map.put(headerList.get(i), stringList.get(i));
					}
					mapList.add(map);
				}
			}
			System.out.println(mapList);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(mapList, HttpStatus.OK);
	}
}
