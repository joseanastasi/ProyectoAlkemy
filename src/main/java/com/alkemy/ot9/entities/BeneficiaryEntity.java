package com.alkemy.ot9.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "beneficiary")
public class BeneficiaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "native")
    @GenericGenerator(name="native",strategy = "native")
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @NotEmpty
    private String dni;
    @Email
    private String email;
    @NotEmpty
    private String phone;
    @NotEmpty
    private String town;
    @JoinTable(name="rel_beneficiary_event",joinColumns = @JoinColumn(name="beneficiary",nullable=false),
            inverseJoinColumns = @JoinColumn(name = "event",nullable = false))
    @ManyToMany(cascade = CascadeType.ALL)
    private List<EventEntity> event;

    public void addEvent(EventEntity eventEntity){
        if(this.event == null){
            this.event = new ArrayList<>();
        }
        try{
            if (!this.event.contains(eventEntity) && eventEntity.isActive() == true){
                this.event.add(eventEntity);
            }
        }catch (Exception e){
            System.out.println("No puede inscribirse en 2 eventos");
        }

    }
    public List<EventEntity> getEvent() {
        return event;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @Override
    public String toString() {
        return "BeneficiaryEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dni='" + dni + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", town='" + town + '\'' +
                ", eventList=" + event +
                '}';
    }
}