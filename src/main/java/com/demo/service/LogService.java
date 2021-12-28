package com.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LogService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	public void log(String message) {
		logger.info(message);
	}
}
