package com.cakkie.backend.repository;

import com.cakkie.backend.model.ForgotPassword;
import com.cakkie.backend.model.admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AdminForgotPasswordRepo extends JpaRepository<ForgotPassword, Integer> {

    @Query(value = "select fp from ForgotPassword fp where fp.otp = ?1 and fp.admin = ?2")
    Optional<ForgotPassword> findByOtpAndAdmin(Integer otp, admin admin);
}
