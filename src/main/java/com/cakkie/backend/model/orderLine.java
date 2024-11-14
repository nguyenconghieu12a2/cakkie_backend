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
@Table(name = "order_line")
public class orderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "product_item_id", nullable = false)
    private productItem productItemId;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private shopOrder orderId;
    @Column(name = "qty", nullable = false)
    private int qty;
    @Column(name = "price", nullable = false)
    private long price;
    @Column(name = "discount_price", nullable = true)
    private Long discountPrice;
    @Column(name = "note", nullable = true, columnDefinition = "TEXT")
    private String note;

    @OneToMany(mappedBy = "orderProductId")
    private List<userReview> userReviewList;
}
