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
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
//the access token sent by the auth server, it will be stored in session.
@EnableOAuth2Sso
@EnableZuulProxy
public class ClientApplication extends WebSecurityConfigurerAdapter{
	
	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}
	
	 /*@Bean
	 public OAuth2RestOperations oAuth2RestOperations(OAuth2ClientContext oauth2ClientContext, OAuth2ProtectedResourceDetails details) {
	    OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(details, oauth2ClientContext);
	    return oAuth2RestTemplate;
	}*/
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.logout().logoutSuccessUrl("/").and()
			.authorizeRequests()
				.antMatchers("/index.html", "/home.html", "/", "/login","/resource/demo").permitAll()
				.anyRequest().authenticated()
				.and()
			.csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
			// @formatter:on
	}

}
