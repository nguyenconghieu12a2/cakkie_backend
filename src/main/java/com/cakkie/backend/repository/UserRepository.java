package com.cakkie.backend.repository;

import com.cakkie.backend.model.userSite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<userSite, Integer> {
}
