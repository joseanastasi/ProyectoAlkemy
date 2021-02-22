package com.alkemy.ot9.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NewsModelNotFoundException extends RuntimeException {
	public NewsModelNotFoundException(String menssage) {
		super(menssage);
	}
}
