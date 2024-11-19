package com.cakkie.backend.dto;

public class AdminLoginResponse {
    private String jwtAdmin;
    private int id;
    private String image;

    public String getJwtAdmin() {
        return jwtAdmin;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public void setJwtAdmin(String jwtAdmin) {
        this.jwtAdmin = jwtAdmin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
