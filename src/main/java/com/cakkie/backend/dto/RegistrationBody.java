package com.cakkie.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class RegistrationBody {
    @NotNull
    private String firstname;

    @NotNull
    private String lastname;

    @NotNull
    @NotBlank
    private String username;

    @NotNull
    private String gender;

    @NotNull
    private LocalDate birthday; // Changed from Date to LocalDate

    @NotNull
    @Email
    private String email;

    @NotNull
    private String phone;

    @NotNull
    private String password;

    // Getters and setters
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthday() { // Updated return type
        return birthday;
    }

    public void setBirthday(LocalDate birthday) { // Updated parameter type
        this.birthday = birthday;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}