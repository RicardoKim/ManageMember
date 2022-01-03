package com.demo.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.demo.dto.ResponseDTO;
import com.demo.dto.TeamDTO;

@ControllerAdvice
public class ExceptionController {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> hadlingdtoInValidException(MethodArgumentNotValidException e, HttpServletRequest request){
		String errorMessage = e.getBindingResult()
			    .getAllErrors()
			    .get(0)
			    .getDefaultMessage();
		ResponseDTO<String> response = ResponseDTO.<String>builder().statusCode(400).error(errorMessage).build();
		return ResponseEntity.badRequest().body(response);
	}
	
	@ExceptionHandler(RuntimeException.class)
	private ResponseEntity<?> handlingRuntimeException(Exception e){
		ResponseDTO<TeamDTO> response = ResponseDTO.<TeamDTO>builder().statusCode(400).error(e.getMessage()).build();
		return ResponseEntity.badRequest().body(response);
	}
	
	@ExceptionHandler(NullPointerException.class)
	private ResponseEntity<?> handlingNullPointerException(Exception e){
		ResponseDTO<String> errorResponse = ResponseDTO.<String>builder().statusCode(204).error(e.getMessage()).build();
		return ResponseEntity.ok().body(errorResponse);
	}
	
}
