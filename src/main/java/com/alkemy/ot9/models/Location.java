package com.alkemy.ot9.models;

public class Location {
	private String id;
	private String nombre;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Location(String id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}	

}
