package com.cakkie.backend.repository;

import com.cakkie.backend.model.userReview;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserReviewRepository extends CrudRepository<userReview, Integer> {
    @Query("SELECT ur FROM userReview ur " +
            "JOIN ur.orderProductId ol " +
            "JOIN ol.productItemId pi " +
            "WHERE pi.proId.id = :productId " +
            "AND ur.isDeleted = 1 " +
            "AND ur.statusId.id = 1 ")
    List<userReview> findReviewsByProductId(int productId);
}
