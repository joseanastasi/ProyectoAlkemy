package com.alkemy.ot9.entities;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


import com.alkemy.ot9.models.ContributorType;
import com.alkemy.ot9.models.PotentialContributor;

@Entity
@Table(name = "contributor")
public class ContributorEntity  {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contributor_id")
	private Long id;
	@NotEmpty
	private String name;
	@NotEmpty
	private String surname;
	@NotNull
	private ContributorType type;
	@NotEmpty
	@Email
	private String email;
	@NotEmpty
	private String phone;
	@NotNull
	private boolean active;
	@NotNull
	private PotentialContributor potentialContributor;
	@OneToMany(mappedBy = "contributor", fetch = FetchType.LAZY,
    		cascade = CascadeType.ALL)
	private Set<TransactionEntity> transaction;
	private String problematic;
	private String healthInitiative;
	@NotEmpty
	private String province;
	@NotEmpty
	private String location;
	
	
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