package com.cakkie.backend.repository;

import com.cakkie.backend.model.userStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserStatusRepository extends CrudRepository<userStatus, Integer> {
    Optional<userStatus> findByStatus(String status);
}
