package com.shopme.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontEndMainController {

	@GetMapping("")
	public String viewHomePage() {
		return "index";
	}

}
