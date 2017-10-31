package com.oauth2.zuul.authserver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

	@Resource(name = "tokenServices")
    ConsumerTokenServices tokenServices;
	
    @Resource(name = "tokenStore")
    TokenStore tokenStore;
    
    @RequestMapping(method = RequestMethod.POST, value = "/revokeToken")
    @ResponseBody
    public void revokeToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.contains("bearer")) {
            String tokenId = authorization.substring("Bearer".length() + 1);
            tokenServices.revokeToken(tokenId);
        }
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/refresh_token")
    @ResponseBody
    public String getRefreshToken(HttpServletRequest request) {
        
    	String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.contains("bearer")) {
            String tokenId = authorization.substring("Bearer".length() + 1);
            OAuth2RefreshToken refreshToken=tokenStore.readRefreshToken(tokenId);
            return refreshToken.getValue();
        }
       
       return null;
    }
}
