package com.cakkie.backend.repository.adminReview;

import com.cakkie.backend.model.userReview;
import com.cakkie.backend.model.userReviewStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AdminReviewStatusRepository extends CrudRepository<userReviewStatus, Integer> {
    Optional<userReviewStatus> findById(int id);
}