package com.oauth2.zuul.resourceserver.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Daniel de la Torre
 *
 */
@RestController
@RequestMapping("test")
public class TestController {

	@RequestMapping(method = GET)
	//@PreAuthorize("#oauth2.clientHasRole('ROLE_TRUSTED_CLIENT')")//this is the way to protect resources in the clients level
	//@PreAuthorize("hasRole('ADMIN')") //this is to protect the resource in the users level
	public Map<String, String> getTest() {
		Map<String, String> map = new HashMap<>();
		map.put("data", "protected_data");
		return map;
	}
}