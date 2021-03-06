package com.demo.exception;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter{
	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException{
      try{
          filterChain.doFilter(request,response);
      } catch (UnauthorizedException ex){
          log.error("exception handler filter");
          setErrorResponse(response, ex.getMessage());
      }
    }
	
	private void setErrorResponse(HttpServletResponse response, String errorMessage) throws IOException{
		JSONObject data = new JSONObject();
		data.put("error", errorMessage);
		data.put("statusCode", 401);
		data.put("data", null);
		response.setStatus(401);
		response.setContentType("application/json");
        response.getWriter().write(data.toJSONString());
    }
}
