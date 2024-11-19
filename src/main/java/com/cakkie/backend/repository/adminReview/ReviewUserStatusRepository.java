package com.cakkie.backend.repository.adminReview;

import com.cakkie.backend.model.userStatus;
import org.springframework.data.repository.CrudRepository;

public interface ReviewUserStatusRepository extends CrudRepository<userStatus, Integer> {
}