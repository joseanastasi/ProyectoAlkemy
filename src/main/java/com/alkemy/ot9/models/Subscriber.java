package com.alkemy.ot9.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class Subscriber {
	
	private Long id;
	@NotEmpty(message = "El correo electronico es obligatorio.")
	@Email(message = "Debe ingresar un correo de electronico valido.")
	private String email;
	
	public Subscriber() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Subscriber(Long id, String email) {
		super();
		this.id = id;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
