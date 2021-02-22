package com.alkemy.ot9.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Contributor {

	private Long id;
	@NotEmpty(message = "El nombre del contribuidor es obligatorio.")
	@Pattern(regexp = "^[a-zA-ZñÑáíéóú]\'?[a-zA-ZñÑáíéóú]*(\s[a-zA-ZñÑáíéóú]+)*$", message = "El nombre debe contener unicamente letras.")
	private String name;
	@NotEmpty(message = "El apellido del contribuidor es obligatorio.")
	@Pattern(regexp = "^[a-zA-ZñÑáíéóú]\'?[a-zA-ZñÑáíéóú]*(\s[a-zA-ZñÑáíéóú]+)*$", message = "El apellido debe contener unicamente letras.")
	private String surname;
	@NotNull(message = "El tipo del contribuidor es obligatorio.")
	private ContributorType type;
	@Email(message = "Debe ingresar un correo de electronico valido.")
	@NotEmpty
	@Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message = "Debe ingresar un correo de electronico valido.")
	private String email;
	@NotEmpty(message = "El telefono del contribuidor es obligatorio.")
	@Pattern(regexp = "[0-9+]+", message = "El felefono no puede contener letras.")
	private String phone;
	@NotNull
	private boolean active;
	@NotNull(message = "El tipo del contribuidor es obligatorio.")
	private PotentialContributor potentialContributor;

	private String problematic;
	private String healthInitiative;
	@NotEmpty(message = "Debe seleccionar una provincia.")
	private String province;
	@NotEmpty(message = "Debe seleccionar una localidad.")
	private String location;

	public Contributor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Contributor(String name, String surname, ContributorType type, String email, String phone, boolean active,
			PotentialContributor potentialContributor, String province, String location) {
		super();
		this.name = name;
		this.surname = surname;
		this.type = type;
		this.email = email;
		this.phone = phone;
		this.active = active;
		this.potentialContributor = potentialContributor;
		this.province = province;
		this.location = location;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public PotentialContributor getPotentialContributor() {
		return potentialContributor;
	}

	public void setPotentialContributor(PotentialContributor potentialContributor) {
		this.potentialContributor = potentialContributor;
	}

	public String getProblematic() {
		return problematic;
	}

	public void setProblematic(String problematic) {
		this.problematic = problematic;
	}

	public String getHealthInitiative() {
		return healthInitiative;
	}

	public void setHealthInitiative(String healthInitiative) {
		this.healthInitiative = healthInitiative;
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