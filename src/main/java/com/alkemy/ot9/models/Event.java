package com.alkemy.ot9.models;
import com.alkemy.ot9.entities.BeneficiaryEntity;

import java.sql.Date;
import java.util.List;

public class Event {
    private Long id;
    private String title;
    private String eventType;
    private String image;
    private String content;
    private String address;
    private Date startDate;
    private Date endDate;
    private int numberOfBeneficiaries;
    private boolean active;
    private List<Beneficiary> beneficiary;
    private int enrolled;

    public Event() {
    }

    public Event(Long id, String title, String eventType, String image, String content, String address, Date startDate, Date endDate, int numberOfBeneficiaries, List<Beneficiary> beneficiary, int enrolled) {
        this.id = id;
        this.title = title;
        this.eventType = eventType;
        this.image = image;
        this.content = content;
        this.address = address;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfBeneficiaries = numberOfBeneficiaries;
        this.beneficiary = beneficiary;
        this.enrolled = enrolled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getNumberOfBeneficiaries() {
        return numberOfBeneficiaries;
    }

    public void setNumberOfBeneficiaries(int numberOfBeneficiaries) {
        this.numberOfBeneficiaries = numberOfBeneficiaries;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Beneficiary> getBeneficiaries() {
        return beneficiary;
    }

    public void setBeneficiaries(List<Beneficiary> beneficiaryEntityList) {
        this.beneficiary = beneficiary;
    }

	public int getEnrolled() {
		return enrolled;
	}

	public void setEnrolled(int enrolled) {
		this.enrolled = enrolled;
	}
    
}