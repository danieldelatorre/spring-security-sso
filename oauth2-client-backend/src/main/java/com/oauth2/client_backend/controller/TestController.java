package com.oauth2.client_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientbackend")
public class TestController {
	
	@Autowired
	private OAuth2RestTemplate template;
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String getTestResource() {
	 
		//resource server http://localhost:9001/resource/test
		//Zuul proxy http://localhost:8080/resource/test
		String s = template.getForObject("http://localhost:9001/resource/test", String.class);
		return s;
	}
}

