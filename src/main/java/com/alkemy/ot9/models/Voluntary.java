package com.alkemy.ot9.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

public class Voluntary {

    private Long id;
    @NotEmpty(message = "El nombre del voluntario es obligatorio.")
    @Pattern(regexp = "^[a-zA-ZñÑáíéóú]\'?[a-zA-ZñÑáíéóú]*(\s[a-zA-ZñÑáíéóú]+)*$", message = "El nombre debe contener unicamente letras.")
    private String name;
    @NotEmpty(message = "El apellido del voluntario es obligatorio.")
    @Pattern(regexp = "^[a-zA-ZñÑáíéóú]\'?[a-zA-ZñÑáíéóú]*(\s[a-zA-ZñÑáíéóú]+)*$", message = "El apellido debe contener unicamente letras.")
    private String surname;
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message = "Debe ingresar un correo de electronico valido.")
    private String email;
    @NotEmpty(message = "El telefono es obligatorio.")
    @Pattern(regexp = "[0-9+]+", message = "El telefono no puede contener letras.")
    private String phone;
    private LocalDate dateTime;

    public LocalDate getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
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

}
