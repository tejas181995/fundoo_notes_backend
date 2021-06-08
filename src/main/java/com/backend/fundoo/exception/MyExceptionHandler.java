package com.backend.fundoo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.backend.fundoo.response.Response;

@RestControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ UserNotVerifiedException.class, UserNotFoundException.class })
	public ResponseEntity<Response> userNotVerified(Exception ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(ex.getMessage(), 400));
	}

	@ExceptionHandler(NoteNotFoundException.class)
	public ResponseEntity<Response> noteException(NoteNotFoundException ex) {
		Response response = new Response(ex.getMessage(), 400);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}