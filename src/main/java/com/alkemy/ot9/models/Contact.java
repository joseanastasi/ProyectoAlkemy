package com.alkemy.ot9.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Contact {
	@NotEmpty(message = "El nombre es obligatorio.")
	@Pattern(regexp = "[a-zA-Z]+", message = "El nombre debe contener unicamente letras.")
	private String name;
	@NotEmpty(message = "El apellido es obligatorio.")
	@Pattern(regexp = "[a-zA-Z]+", message = "El apellido debe contener unicamente letras.")
	private String surname;
	@NotNull(message = "El tipo es obligatorio.")
	private ContributorType type;
	@NotEmpty(message = "El correo electronico es obligatorio.")
	@Email(message = "Debe ingresar un correo de electronico valido.")
	private String email;
	@NotEmpty(message = "El telefono es obligatorio.")
	@Pattern(regexp = "[0-9+]+", message = "El felefono no puede contener letras.")
	private String phone;
	@NotEmpty(message = "Debe seleccionar una provincia.")
	private String province;
	@NotEmpty(message = "Debe seleccionar una localidad.")
	private String location;
	@NotEmpty(message = "Por favor ingresa un mensaje.")
	private String message;
		
	
	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public ContributorType getType() {
		return type;
	}
	public void setType(ContributorType type) {
		this.type = type;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	
}