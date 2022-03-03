package com.police_resource_manager.prms.exceptions;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.jsonwebtoken.ExpiredJwtException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
//		String message = "";
		StringBuilder message = new StringBuilder();
		
		ex.getBindingResult().getAllErrors().forEach((error)->{
			
			message.append("<p>"+error.getDefaultMessage()+"</p>");
		});
		
		ApiExceptionFormatter apiException = new ApiExceptionFormatter(
				message.toString(),
				HttpStatus.BAD_REQUEST,
				ZonedDateTime.now(ZoneId.of("Z"))
		);
		
		
		return new ResponseEntity<Object>(apiException, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ApiRequestException.class)
	public ResponseEntity<Object> handleApiRequestException(ApiRequestException e){
		// 1. Create payload containing exception details
		HttpStatus badRequest = HttpStatus.BAD_REQUEST;
		
		ApiExceptionFormatter apiException = new ApiExceptionFormatter(
				e.getMessage(),
				badRequest,
				ZonedDateTime.now(ZoneId.of("Z"))
		);
		
		//2. Return response entity
		return new ResponseEntity<>(apiException, badRequest);
	}
	
	@ExceptionHandler(IllegalQueryFormatException.class)
	public ResponseEntity<Object> handleIllegalQueryFormatException(IllegalQueryFormatException e){
		HttpStatus badRequest = HttpStatus.BAD_REQUEST;
		
		ApiExceptionFormatter apiException = new ApiExceptionFormatter(
				e.getMessage(),
				badRequest,
				ZonedDateTime.now(ZoneId.of("Z"))
		);
		
		return new ResponseEntity<>(apiException, badRequest);
	}
	
	@ExceptionHandler(InvalidJwtException.class)
	public ResponseEntity<Object> handleInvalidJwtException(InvalidJwtException e){
		HttpStatus unauthorizedRequest = HttpStatus.UNAUTHORIZED;
		
		ApiExceptionFormatter apiException = new ApiExceptionFormatter(
				e.getMessage(),
				unauthorizedRequest,
				ZonedDateTime.now(ZoneId.of("Z"))
		);
		
		return new ResponseEntity<>(apiException, unauthorizedRequest);
	}
	
	@ExceptionHandler(NoOfficerFoundException.class)
	public ResponseEntity<Object> handleNoOfficerFoundException(NoOfficerFoundException e){
		HttpStatus notFound = HttpStatus.NOT_FOUND;
		
		ApiExceptionFormatter apiException = new ApiExceptionFormatter(
				e.getMessage(),
				notFound,
				ZonedDateTime.now(ZoneId.of("Z"))
		);
		
		return new ResponseEntity<>(apiException, notFound);
	}
	
	
	
}
