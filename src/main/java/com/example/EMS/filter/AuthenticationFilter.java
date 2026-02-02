package com.example.EMS.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        //Skip Login, swagger ui and open-api doc
        if(path.equals("/v1/auth/login") || path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")){
            logger.info("Bypassing authentication!!");
            filterChain.doFilter(request,response);
            return;
        }

        String token = getCookieValue(request,"auth_token");
        if(token == null || token.isEmpty()){
            logger.info("Corresponding cookie doesn't exist!!");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"No auth_cookie found");
        } else if (!"demo_token_value".equals(token)) {
            logger.info("Invalid cookie!!");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Invalid cookie found");
            return;
        }

        logger.info("Login Successfull for: "+path);

        //Setting secruity context
        UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken("user",null,null);
        SecurityContextHolder.getContext().setAuthentication(authtoken);

        filterChain.doFilter(request,response);
    }

    private String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    logger.info("Cookie: " + cookieName);
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
