package com.cakkie.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_site")
public class userSite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "firstname", nullable = true)
    private String firstname;
    @Column(name = "lastname", nullable = true)
    private String lastname;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "gender", nullable = true)
    private String gender;
    @Column(name = "birthday", nullable = true)
    private Date birthday;
    @Column(name = "image", nullable = true, columnDefinition = "TEXT")
    private String image;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "phone", nullable = true, length = 11)
    private String phone;
    @Column(name = "password", nullable = true, length = 32)
    private String password;
    @ManyToOne
    @JoinColumn(name = "status")
    private userStatus statusId;
    @Column(name = "account_create_date", nullable = false)
    private Date accountCreateDate;

    @Column(name = "banned_reason", nullable = true, columnDefinition = "TEXT")
    private String bannedReason;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<userAddress> userAddresses;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<userPaymentMethod> userPaymentMethodList;

    @OneToMany(mappedBy = "userId")
    private List<shopOrder> shopOrderList;

    @OneToMany(mappedBy = "userId")
    private List<productCart> productCartList;

    @OneToMany(mappedBy = "userId")
    private List<shoppingCart> shoppingCartList;

    @OneToMany(mappedBy = "userId")
    private List<userReview> userReviewList;
}
