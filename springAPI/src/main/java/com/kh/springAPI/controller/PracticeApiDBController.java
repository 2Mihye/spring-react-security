package com.kh.springAPI.controller;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/csv-data")
public class PracticeApiDBController {
	@GetMapping(produce)
	public ResponseEntity<InputStreamResource> csvData() {
		try {
			String csvFileName = "contractState.csv";
			Path csvFilePath = Paths.get(getClass().getResource(csvFileName).toURI());
		
			InputStreamResource resource = new Input
		}
	}
}
