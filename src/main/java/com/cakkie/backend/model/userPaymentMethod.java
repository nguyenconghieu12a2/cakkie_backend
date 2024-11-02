package com.cakkie.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_payment_method")
public class userPaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private userSite userId;
    @ManyToOne
    @JoinColumn(name = "payment_type_id", nullable = false)
    private paymentMethod paymentTypeId;
    @Column(name = "is_default", nullable = false)
    private int isDefault;

    @OneToMany(mappedBy = "paymentMethod")
    private List<shopOrder> shopOrderList;
}
