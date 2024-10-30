package com.cakkie.backend.repository;

import com.cakkie.backend.model.admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminLoginRepo extends JpaRepository<admin, Integer> {
    Optional<admin> findByUsernameIgnoreCase(String username);

    Optional<admin> findByEmailIgnoreCase(String email);

//    Optional<admin> findByImageIgnoreCase(String image);

}
