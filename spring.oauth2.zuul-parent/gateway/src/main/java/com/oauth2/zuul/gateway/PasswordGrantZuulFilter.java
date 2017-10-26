package com.oauth2.zuul.gateway;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class PasswordGrantZuulFilter extends ZuulFilter {
    
    @Autowired
    private OAuth2ClientContext clientContext;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
    	RequestContext ctx = RequestContext.getCurrentContext();
        String header = ctx.getRequest().getHeader(HttpHeaders.AUTHORIZATION);

        return header != null && header.toLowerCase().startsWith("bearer");
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        if (clientContext.getAccessToken() == null) {

            String header = ctx.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
            String bearer = header.substring("bearer".length()).trim();
            OAuth2AccessToken token=new DefaultOAuth2AccessToken(bearer);
            clientContext.setAccessToken(token);
        }

        ctx.addZuulRequestHeader(HttpHeaders.AUTHORIZATION, OAuth2AccessToken.BEARER_TYPE + " " + clientContext.getAccessToken().getValue());

        return null;
    }

}
