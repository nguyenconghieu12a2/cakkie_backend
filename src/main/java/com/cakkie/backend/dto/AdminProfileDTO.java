package com.cakkie.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminProfileDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String adminImage;
}
