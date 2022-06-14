package com.imdb.demo.Registration.entity;


import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table(name = "Registration")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;

    @Column(unique = true, name = "name")
    public String name;

    @Email
    @Column(unique = true, name = "email")
    public String email;

    @Column(name = "otp")
    private String otp;

    @Column(name = "expiration")
    private String expiration;

    public Registration() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getExpiration() {
        return expiration;
    }

    public String setExpiration(String expiration) {
        this.expiration = expiration;
        return expiration;
    }

    public Registration(String name, String email, String otp, String expiration) {
        this.name = name;
        this.email = email;
        this.otp = otp;
        this.expiration = expiration;
    }


}
