package com.cakkie.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_address")
public class userAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private userSite userId;
    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private address addressId;
    @Column(name = "is_default", nullable = false)
    private int isDefault;
    @Column(name = "is_deleted", nullable = false)
    private int isDeleted;
}
