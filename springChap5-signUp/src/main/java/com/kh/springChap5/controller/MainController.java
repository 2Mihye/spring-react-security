package com.kh.springChap5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
	@GetMapping("/")
	public String showMain() {
		return "index";
	}
}
