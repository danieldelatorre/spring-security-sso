package com.oauth2.zuul.resourceserver;

import java.security.Principal;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Daniel de la Torre
 *
 */
@SpringBootApplication
@EnableResourceServer
@RestController
public class ResourceServerApplication {

	@Resource(name = "tokenStore")
    private TokenStore tokenStore;
	
    public static void main(String[] args) {
        SpringApplication.run(ResourceServerApplication.class, args);
    }
    
    @RequestMapping("/")
	public Message home(Principal user) {
		return new Message(user.getName(),tokenStore.findTokensByClientId("ui1").toString());
	}

}

class Message {
	
	private String id;
	private String token;

	Message() {
	}

	public Message(String id,String token) {
		this.id=id;
		this.token = token;
	}
	
	public String getId()
	{
		return id;
	}


	public String getToken() {
		return token;
	}
}
