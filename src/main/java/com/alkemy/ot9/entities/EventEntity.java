package com.alkemy.ot9.entities;
import com.sun.istack.NotNull;
import org.hibernate.annotations.GenericGenerator;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "event")
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "native")
    @GenericGenerator(name="native",strategy = "native")
    private Long id;
    @NotNull
    @Column @Size(min=1, max=250)
    private String title;
    @Column @NotNull
    private String eventType;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private String image;
    @NotNull
    @Column @Size(min=1, max=300)
    private String content;
    @Column 
    private String address;
    @NotNull
    @Column
    private Date startDate;
    @NotNull
    @Column
    private Date endDate;
    @Column
    private boolean active;
    @Column
    private int numberOfBeneficiaries;
    @ManyToMany(mappedBy = "event",cascade = CascadeType.ALL)
    private List<BeneficiaryEntity> beneficiary;
    @Column
    private int enrolled;

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getNumberOfBeneficiaries() {
        return numberOfBeneficiaries;
    }

    public void setNumberOfBeneficiaries(int numberOfBeneficiaries) {
        this.numberOfBeneficiaries = numberOfBeneficiaries;
    }

    public List<BeneficiaryEntity> getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(List<BeneficiaryEntity> beneficiary) {
        this.beneficiary = beneficiary;
    }

	public int getEnrolled() {
		return enrolled;
	}

	public void setEnrolled(int enrolled) {
		this.enrolled = enrolled;
	}
    
    

}