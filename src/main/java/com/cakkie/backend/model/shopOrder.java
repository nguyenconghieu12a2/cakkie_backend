package com.cakkie.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shop_order")
public class shopOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private userSite userId;
    @ManyToOne
    @JoinColumn(name = "shipping_method_id", nullable = false)
    @JsonBackReference
    private shippingMethod shippingMethodId;
    @ManyToOne
    @JoinColumn(name = "shipping_address_id", nullable = false)
    @JsonBackReference
    private address shippingAddressId;
    @ManyToOne
    @JoinColumn(name = "payment_method", nullable = false)
    @JsonBackReference
    private userPaymentMethod paymentMethod;
    @ManyToOne
    @JoinColumn(name = "order_status_id", nullable = false)
    @JsonBackReference
    private orderStatus orderStatusId;
    @Column(name = "order_total", nullable = false)
    private long orderTotal;
    @ManyToOne
    @JoinColumn(name = "coupons", nullable = true)
    @JsonBackReference
    private coupons coupons;
    @Column(name = "order_date", nullable = false)
    private Date orderDate;
    @Column(name = "approved_date", nullable = true)
    private Date approvedDate;
    @Column(name = "shipping_date", nullable = true)
    private Date shippingDate;
    @Column(name = "arrived_date", nullable = true)
    private Date arrivedDate;
    @Column(name = "canceled_date", nullable = true)
    private Date canceledDate;
    @Column(name = "canceled_reason", nullable = true)
    private String canceledReason;

    @OneToMany(mappedBy = "orderId")
    @JsonIgnore
    private List<orderLine> orderLinesList;
}
