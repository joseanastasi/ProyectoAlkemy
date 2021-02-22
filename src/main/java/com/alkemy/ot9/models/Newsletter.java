package com.alkemy.ot9.models;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.URL;

public class Newsletter {
	
	@NotEmpty(message = "Este campo no puede estar vacio. Por favor ingrese un mensaje para que reciban los subscriptores del newsletter.")
	private String message;
	@NotEmpty(message = "Este campo no puede estar vacio. Por favor ingrese el sitio web de novedades.")
	@URL(message = "Dato invalido. Por favor ingresa una URL. ej: http://ejemplo.org/novedades")
	private String url;	
		
	public Newsletter() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Newsletter(String message, String url) {
		super();
		this.message = message;
		this.url = url;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
