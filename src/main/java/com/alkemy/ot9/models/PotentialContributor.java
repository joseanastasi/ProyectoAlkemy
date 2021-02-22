package com.alkemy.ot9.models;

public enum PotentialContributor {
	EFECTIVE ("EFECTIVE"),
	POTENTIAL ("POTENTIAL");
	
	private final String name;

	private PotentialContributor(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}	
}