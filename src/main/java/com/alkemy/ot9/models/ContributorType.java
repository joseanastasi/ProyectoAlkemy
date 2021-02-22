package com.alkemy.ot9.models;

public enum ContributorType {
	ONG ("ONG"),
	HEALTH_CENTER ("Centro de Salud"),
	INDIVIDUAL ("Individuo");
	
	private final String name;

	ContributorType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}	
}
