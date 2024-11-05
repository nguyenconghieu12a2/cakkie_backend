package com.cakkie.backend.repository;

import com.cakkie.backend.model.userPaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPaymentMethodRepository extends JpaRepository<userPaymentMethod, Integer> {
}
