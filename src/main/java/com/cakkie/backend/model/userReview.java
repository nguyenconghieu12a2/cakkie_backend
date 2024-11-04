package com.cakkie.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_review")
public class userReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "order_product_id", nullable = false)
    private orderLine orderProductId;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private userSite userId;
    @Column(name = "rating", nullable = true)
    private int rating;
    @Column(name = "feedback", nullable = true, columnDefinition = "TEXT")
    private String feedback;
    @Column(name = "review_image", nullable = true, columnDefinition = "TEXT")
    private String reviewImage;
    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private userReviewStatus statusId;
    @Column(name = "comment_date", nullable = false)
    private Date commentDate;
    @Column(name = "approved_date", nullable = true)
    private Date approvedDate;
    @Column(name = "valid_date", nullable = true)
    private Date validDate;
    @Column(name = "is_hide", nullable = true)
    private int isHide;
    @Column(name = "is_deleted", nullable = false)
    private int isDeleted;
}
