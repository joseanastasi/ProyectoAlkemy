package com.alkemy.ot9.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class Team {

    private Long id;
    @NotEmpty(message = "El nombre del contribuidor es obligatorio.")
    @Pattern(regexp = "^[a-zA-ZñÑáíéóú]\'?[a-zA-ZñÑáíéóú]*(\s[a-zA-ZñÑáíéóú]+)*$", message = "El nombre debe contener unicamente letras.")
    private String name;
    @NotEmpty(message = "El apellido del contribuidor es obligatorio.")
    @Pattern(regexp = "^[a-zA-ZñÑáíéóú]\'?[a-zA-ZñÑáíéóú]*(\s[a-zA-ZñÑáíéóú]+)*$", message = "El apellido debe contener unicamente letras.")
    private String surname;
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message = "Debe ingresar un correo de electronico valido.")
    private String email;
    @NotEmpty(message = "El telefono del contribuidor es obligatorio.")
    @Pattern(regexp = "[0-9+]+", message = "El telefono no puede contener letras.")
    private String phone;
    private TeamType type;
    private String image;
    private String content;
    private Organization organization;

    public TeamType getType() {
        return type;
    }

    public void setType(TeamType type) {
        this.type = type;
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

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

}
