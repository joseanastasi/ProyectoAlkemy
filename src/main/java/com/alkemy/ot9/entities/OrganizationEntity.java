package com.alkemy.ot9.entities;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "organizations")
public class OrganizationEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	@NotBlank
	@NotNull
	private String name;
	@NotBlank
	@Column(columnDefinition="TEXT")
	private String vision;
	@NotBlank
	@Column(columnDefinition="TEXT")
	private String mission;
	@NotBlank
	private String phone;
	@NotBlank
	private String email;
	@NotBlank
	private String address;
	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private String logo;
	@Column
	private boolean active;
	@OneToMany(mappedBy = "organization", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<TeamEntity> team;
	@Column
	private String emailServer;
	@Column
	private String emailServerPort;
	@Column
	private String emailUser;
	@Column
	private String emailPassword;
		

	/**
	 * @return the organizationId
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the organizationId to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the vision
	 */
	public String getVision() {
		return vision;
	}

	/**
	 * @param vision the vision to set
	 */
	public void setVision(String vision) {
		this.vision = vision;
	}

	/**
	 * @return the mission
	 */
	public String getMission() {
		return mission;
	}

	/**
	 * @param mission the mission to set
	 */
	public void setMission(String mission) {
		this.mission = mission;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the logo
	 */
	public String getLogo() {
		return logo;
	}

	/**
	 * @param logo the logo to set
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getEmailServer() {
		return emailServer;
	}

	public void setEmailServer(String emailServer) {
		this.emailServer = emailServer;
	}

	public String getEmailServerPort() {
		return emailServerPort;
	}

	public void setEmailServerPort(String emailServerPort) {
		this.emailServerPort = emailServerPort;
	}

	public String getEmailUser() {
		return emailUser;
	}

	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}

	public String getEmailPassword() {
		return emailPassword;
	}

	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}
	
	

}
