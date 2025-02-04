package com.test.zoom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VueController {
	@GetMapping("/")
	public String showVuePage() {
		return "/index.html";
	}

	@GetMapping("/main/rootPage")
	public String showLoginSuccessPage() {
		return "/index.html";
	}
}