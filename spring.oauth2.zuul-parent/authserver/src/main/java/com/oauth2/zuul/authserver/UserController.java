package com.oauth2.zuul.authserver;

import java.security.Principal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@RequestMapping("/user")
	public Principal userName(Principal user) {
		return user;
	}
}
