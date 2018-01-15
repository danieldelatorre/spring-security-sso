package com.oauth2.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class CustomPostZuulFilter extends ZuulFilter {

	//@Autowired
	//private OAuth2RestOperations oAuth2RestOperations;
	
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Object run() {
        final RequestContext ctx = RequestContext.getCurrentContext();
        logger.info("in zuul filter " + ctx.getRequest().getRequestURI());

        final String requestURI = ctx.getRequest().getRequestURI();
        final String requestMethod = ctx.getRequest().getMethod();
       
        try {
            final InputStream is = ctx.getResponseDataStream();
            String responseBody = IOUtils.toString(is, "UTF-8");
            /*if (responseBody.contains("refresh_token")) {
                final Map<String, Object> responseMap = mapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {
                });
                final String refreshToken = responseMap.get("refresh_token").toString();
                responseMap.remove("refresh_token");
                responseBody = mapper.writeValueAsString(responseMap);

                final Cookie cookie = new Cookie("refreshToken", refreshToken);
                cookie.setHttpOnly(true);
                // cookie.setSecure(true);
                cookie.setPath(ctx.getRequest().getContextPath() + "/oauth/token");
                cookie.setMaxAge(2592000); // 30 days

				ctx.getResponse().addCookie(cookie);
                logger.info("refresh token = " + refreshToken);

            }*/
            if (requestURI.contains("revokeToken") && requestMethod.equals("POST")) {
            	HttpSession session = ctx.getRequest().getSession(false);
            	if(session!=null) {
            		session.invalidate();
            	}
            }
            ctx.setResponseBody(responseBody);

        } catch (final IOException e) {
            logger.error("Error occured in zuul post filter", e);
        }
        return null;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public String filterType() {
        return "post";
    }

}
