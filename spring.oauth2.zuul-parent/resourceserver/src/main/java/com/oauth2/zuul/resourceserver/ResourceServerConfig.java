package com.oauth2.zuul.resourceserver;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

/**
 * 
 * @author Filip Lindby
 *
 */
/*@Configuration
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PropertySource({ "classpath:persistence.properties" })*/
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	private static final String RESOURCE_ID = "resourceService1";
	
	@Autowired
	private Environment env;

	@Autowired
	private DataSource dataSource;

	@Override
	public void configure(HttpSecurity http) throws Exception {				
		http
			.requestMatchers().antMatchers("/**")
		.and()
    		.authorizeRequests()		
    		.antMatchers("/test/**").access("hasRole('END_USER')")//equals->@PreAuthorize("hasRole('END_USER') in the TestController
    		.antMatchers("/demo/**").permitAll()
    		.anyRequest().authenticated();		
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {			
		resources
			.resourceId(RESOURCE_ID)
			.tokenStore(tokenStore());
	}

	/*@Bean
	public TokenStore tokenStore() {
		return new InMemoryTokenStore();
	}
	
	/*@Bean
	public JdbcTokenStore tokenStore() {
		return new JdbcTokenStore(dataSource);
	}*/
	
	   @Bean
	    @Primary
	    public DefaultTokenServices tokenServices() {
	        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
	        defaultTokenServices.setTokenStore(tokenStore());
	        return defaultTokenServices;
	    }

	    // JDBC token store configuration

	    @Bean
	    public DataSource dataSource() {
	        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
	        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
	        dataSource.setUrl(env.getProperty("jdbc.url"));
	        dataSource.setUsername(env.getProperty("jdbc.user"));
	        dataSource.setPassword(env.getProperty("jdbc.pass"));
	        return dataSource;
	    }

	    @Bean
	    public TokenStore tokenStore() {
	        return new JdbcTokenStore(dataSource());
	    }

}
