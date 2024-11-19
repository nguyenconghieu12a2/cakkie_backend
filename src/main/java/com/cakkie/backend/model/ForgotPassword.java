package com.cakkie.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "forgot_password")
public class ForgotPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fpid;

    @Column(name = "otp", nullable = true)
    private Integer otp;

    @Column(name = "expirationTime", nullable = true)
    private Date expirationTime;

    @OneToOne
    private admin admin;
}
