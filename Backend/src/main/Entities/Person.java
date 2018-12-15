package Entities;

import Interfaces.People;

public class Person implements People {

    public enum KeyType {
        NAME,
        SURNAME,
        DNI,
        TELEPHONE,
        EMAIL
    };

    private String dni;
    private String name;
    private String surname;
    private String telephone;
    private String email;

    public Person(String dni, String name, String surname, String telephone, String email) {
        this.dni = dni;
        this.name = name;
        this.surname = surname;
        this.telephone = telephone;
        this.email = email;
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
