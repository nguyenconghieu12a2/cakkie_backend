package com.cakkie.backend.repository.adminReview;

import com.cakkie.backend.model.userReview;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AdminReviewRepository extends CrudRepository<userReview, Integer> {
    Optional<userReview> findById(int id);

    @Query("SELECT ur FROM userReview ur " +
            "JOIN ur.userId u " +
            "JOIN ur.orderProductId ol " +
            "JOIN ol.productItemId pi " +
            "JOIN pi.proId p " +
            "WHERE ur.statusId.id = 2" +
            "ORDER BY ur.commentDate ASC")
    List<userReview> findPendingReviews();

    @Query("SELECT ur FROM userReview ur " +
            "JOIN ur.userId u " +
            "JOIN ur.orderProductId ol " +
            "JOIN ol.productItemId pi " +
            "JOIN pi.proId p " +
            "WHERE ur.statusId.id = 1" +
            "ORDER BY ur.approvedDate ASC")
    List<userReview> findApprovedReviews();

    @Query("SELECT ur FROM userReview ur " +
            "JOIN ur.userId u " +
            "JOIN ur.orderProductId ol " +
            "JOIN ol.productItemId pi " +
            "JOIN pi.proId p " +
            "WHERE ur.statusId.id = 3" +
            "ORDER BY ur.rejectedDate ASC")
    List<userReview> findRejectedReviews();
}