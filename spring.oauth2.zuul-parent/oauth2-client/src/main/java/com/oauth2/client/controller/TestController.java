package com.oauth2.client.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class TestController {
	
	//@Autowired
	private OAuth2RestTemplate template;
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String getTestResource() {
	 
		//resource server http://localhost:9001/resource/test
		//Zuul proxy http://localhost:8080/resource/test
		String s = template.getForObject("http://localhost:9001/resource/test", String.class);
		return s;
	}
}
