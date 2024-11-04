package com.cakkie.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class EditBody {
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
    private Date birthday;
    @NotNull
    private String phone;
    @NotNull
    private String email;

    public @NotNull String getEmail() {
        return email;
    }

    public void setEmail(@NotNull String email) {
        this.email = email;
    }

    public @NotNull String getFirstname() {
        return firstname;
    }

    public void setFirstname(@NotNull String firstname) {
        this.firstname = firstname;
    }

    public @NotNull String getLastname() {
        return lastname;
    }

    public void setLastname(@NotNull String lastname) {
        this.lastname = lastname;
    }

    public @NotNull @NotBlank String getUsername() {
        return username;
    }

    public void setUsername(@NotNull @NotBlank String username) {
        this.username = username;
    }

    public @NotNull String getGender() {
        return gender;
    }

    public void setGender(@NotNull String gender) {
        this.gender = gender;
    }

    public @NotNull Date getBirthday() {
        return birthday;
    }

    public void setBirthday(@NotNull Date birthday) {
        this.birthday = birthday;
    }

    public @NotNull String getPhone() {
        return phone;
    }

    public void setPhone(@NotNull String phone) {
        this.phone = phone;
    }
}
