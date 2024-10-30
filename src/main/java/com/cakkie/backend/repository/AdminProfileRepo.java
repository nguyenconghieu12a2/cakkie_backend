package com.cakkie.backend.repository;

import com.cakkie.backend.model.admin;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminProfileRepo extends JpaRepository<admin, Integer> {
    Optional<admin> findById(int id);
    Optional<admin> findByEmail(String email);

//    @Transactional
//    @Modifying
//    @Query(value = "UPDATE admin SET password = :password WHERE :email", nativeQuery = true)
//    int updatePassword(@Param("email") String email, @Param("password") String password);

    @Transactional
    @Modifying
    @Query(value = "UPDATE admin SET password = :password WHERE email = :email", nativeQuery = true)
    int updatePassword(@Param("email") String email, @Param("password") String password);




}
