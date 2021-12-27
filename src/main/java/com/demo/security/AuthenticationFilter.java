package com.demo.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthenticationFilter extends OncePerRequestFilter{
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
		try {
			System.out.println("Security Check");
			String bearerToken = request.getHeader("Authorization");
			if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
				String token = bearerToken.substring(7);
				if(token != null && token.equals("XgEzXpJLnwVwYaJk")) {
					filterChain.doFilter(request, response);
				}
			}
			
		} catch (Exception ex) {
			logger.error("Could not set user authentication in sercurity context",ex);
		}
		
		
	}
}