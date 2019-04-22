package com.singh.talvinder.ImageUpload.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// A response status of not_found will ensure that the application responds 
// with a 404 status when this exception is thrown.
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyFileNotFoundException extends Exception {
	
	public MyFileNotFoundException(String message) {
		super(message);
	}

	public MyFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
