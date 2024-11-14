package com.cakkie.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString // Custom toString method, to prevent recursive calls
@Table(name = "user_site")
public class userSite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "firstname", nullable = true)
    private String firstname;

    @Column(name = "lastname", nullable = true)
    private String lastname;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "gender", nullable = true)
    private String gender;

    @Column(name = "birthday", nullable = true)
    private Date birthday;

    @Column(name = "image", nullable = true, columnDefinition = "TEXT")
    private String image;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone", nullable = true, length = 11)
    private String phone;

    @JsonIgnore
    @Column(name = "password", nullable = true, length = 32)
    private String password;

    @Column(name = "banned_reason", nullable = true, columnDefinition = "TEXT")
    private String bannedReason;

    @ManyToOne
    @JoinColumn(name = "status")
    private userStatus statusId;

    @Column(name = "account_create_date", nullable = false)
    private Date accountCreateDate;

    @JsonIgnore
    @ToString.Exclude  // Prevent recursion in toString method
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<userAddress> userAddresses;

    @JsonIgnore
    @ToString.Exclude  // Prevent recursion in toString method
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.EAGER)
    private List<userPaymentMethod> userPaymentMethodList;

    @JsonIgnore
    @ToString.Exclude  // Prevent recursion in toString method
    @OneToMany(mappedBy = "userId", fetch = FetchType.EAGER)
    private List<shopOrder> shopOrderList;

    @JsonIgnore
    @ToString.Exclude  // Prevent recursion in toString method
    @OneToMany(mappedBy = "userId", fetch = FetchType.EAGER)
    private List<productCart> productCartList;

    @JsonIgnore
    @ToString.Exclude  // Prevent recursion in toString method
    @OneToMany(mappedBy = "userId", fetch = FetchType.EAGER)
    private List<shoppingCart> shoppingCartList;

    @JsonIgnore
    @ToString.Exclude  // Prevent recursion in toString method
    @OneToMany(mappedBy = "userId", fetch = FetchType.EAGER)
    private List<userReview> userReviewList;
}
