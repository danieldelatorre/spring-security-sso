package com.oauth2.client;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableOAuth2Sso
@EnableZuulProxy
public class ClientApplication extends WebSecurityConfigurerAdapter{
	
	//@Autowired
	private OAuth2RestTemplate template;
	
	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.logout().and()
			.authorizeRequests()
				.antMatchers("/index.html", "/home.html", "/", "/login").permitAll()
				.anyRequest().authenticated()
				.and()
			.csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
			// @formatter:on
	}
	
	
	
	//@Bean
	public OAuth2RestTemplate oAuthRestTemplate() {
		//client credentials
	    //ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
		//resourceDetails.setGrantType("client_credentials");
		
		//password
		ResourceOwnerPasswordResourceDetails resourceDetails =new ResourceOwnerPasswordResourceDetails();
		resourceDetails.setClientAuthenticationScheme(AuthenticationScheme.header);
	    resourceDetails.setClientId("ui1");
	    resourceDetails.setClientSecret("ui1-secret");
	    resourceDetails.setAccessTokenUri("http://localhost:9999/uaa/oauth/token");
	    resourceDetails.setScope(Arrays.asList("ui1.read"));
	    resourceDetails.setUsername("steve");
		resourceDetails.setPassword("password");
	    resourceDetails.setGrantType("password");
	    
	    /*

	    When using @EnableOAuth2Client spring creates a OAuth2ClientContext for us:
	    "The OAuth2ClientContext is placed (for you) in session scope to keep the state for different users separate.
	    Without that you would have to manage the equivalent data structure yourself on the server,
	    mapping incoming requests to users, and associating each user with a separate instance of the OAuth2ClientContext."
	    (http://projects.spring.io/spring-security-oauth/docs/oauth2.html#client-configuration)

	    Internally the SessionScope works with a threadlocal to store variables, hence a new thread cannot access those.
	    Therefore we can not use @Async

	    Solution: create a new OAuth2ClientContext that has no scope.
	    *Note: this is only safe when using client_credentials as OAuth grant type!

	     */

	    OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails, new DefaultOAuth2ClientContext());
	    
	    restTemplate.getAccessToken().toString();
	    
	    return restTemplate;
	}

}
