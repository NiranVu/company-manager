package com.example.demo.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.slf4j.Logger;
import jakarta.persistence.NoResultException;

@ControllerAdvice
public class ExceptionController {

	Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	@ExceptionHandler({NoResultException.class})
	public String notFound(NoResultException e) {
		logger.info("INFO", e);
		return "no-data.html";
	}
}
