package com.alkemy.ot9.models;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class Beneficiary {

    private Long id;
    @NotEmpty(message = "El nombre del contribuidor es obligatorio.")
    @Pattern(regexp = "^[a-zA-ZñÑáíéóú]\'?[a-zA-ZñÑáíéóú]*(\s[a-zA-ZñÑáíéóú]+)*$",
            message = "El nombre debe contener unicamente letras.")
    private String name;
    @NotEmpty(message = "El apellido del contribuidor es obligatorio.")
    @Pattern(regexp = "^[a-zA-ZñÑáíéóú]\'?[a-zA-ZñÑáíéóú]*(\s[a-zA-ZñÑáíéóú]+)*$",
            message = "El apellido debe contener unicamente letras.")
    private String surname;
    @NotEmpty(message = "El dni es obligatorio.")
    @Size(min=8, max=12)
    @Pattern(regexp = "[0-9+]+", message = "El dni debe contener unicamente numeros.")
    private String dni;
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
            message = "Debe ingresar un correo de electronico valido.")
    private String email;
    @NotEmpty(message = "El telefono del contribuidor es obligatorio.")
    @Pattern(regexp = "[0-9+]+", message = "El telefono debe contener unicamente numeros.")
    private String phone;
    @NotEmpty(message = "El apellido del contribuidor es obligatorio.")
    @Pattern(regexp = "^[a-zA-ZñÑáíéóú]\'?[a-zA-ZñÑáíéóú]*(\s[a-zA-ZñÑáíéóú]+)*$",
            message = "EL partido debe contener unicamente letras.")
    private String town;
    private List<Event> event;

  

    public void addEvent(Event eventInput){
        if(this.event == null){
            this.event = new ArrayList<>();
        }
        try{
            if (!this.event.contains(eventInput) && eventInput.isActive() == true){
                this.event.add(eventInput);
            }
        }catch (Exception e){
            System.out.println("No puede inscribirse en 2 eventos");
        }

    }
    
    public Beneficiary() {
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

    public List<Event> getEvent() {
        return event;
    }

    public void setEvent(List<Event> event) {
        this.event = event;
    }
}