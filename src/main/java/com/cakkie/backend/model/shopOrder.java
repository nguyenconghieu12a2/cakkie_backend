package com.cakkie.backend.model;

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
    private shippingMethod shippingMethodId;
    @ManyToOne
    @JoinColumn(name = "shipping_address_id", nullable = false)
    private address shippingAddressId;
    @ManyToOne
    @JoinColumn(name = "payment_method", nullable = false)
    private userPaymentMethod paymentMethod;
    @ManyToOne
    @JoinColumn(name = "order_status_id", nullable = false)
    private orderStatus orderStatusId;
    @Column(name = "order_total", nullable = false)
    private long orderTotal;
    @ManyToOne
    @JoinColumn(name = "coupons", nullable = true)
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
    @Column(name = "canceled_reason", nullable = true, columnDefinition = "TEXT")
    private String canceledReason;

    @OneToMany(mappedBy = "orderId")
    private List<orderLine> orderLinesList;
}
