package Entities;

import Interfaces.People;

import java.io.Serializable;

public class Person implements People, Serializable {

    private static final long serialVersionUID = 1L;

    private String dni;
    private String name;
    private String surname;
    private String telephone;
    private String email;

    public Person(String dni, String name, String surname, String telephone, String email) {
        if(People.isDni(dni)) this.dni = dni;
        this.name = name;
        this.surname = surname;
        if(People.isTelephone(telephone)) this.telephone = telephone;
        if(People.isEmail(email)) this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getDni() {
        return dni;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
