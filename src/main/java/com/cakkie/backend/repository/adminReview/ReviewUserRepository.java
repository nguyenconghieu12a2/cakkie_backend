package com.cakkie.backend.repository.adminReview;

import com.cakkie.backend.model.userSite;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ReviewUserRepository extends CrudRepository<userSite, Integer> {
    Optional<userSite> findById(int id);
}
