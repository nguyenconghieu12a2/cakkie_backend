package com.cakkie.backend.repository.adminOthers.customerRV;

import com.cakkie.backend.model.userReviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewStatusRepo extends JpaRepository<userReviewStatus, Integer> {
}
