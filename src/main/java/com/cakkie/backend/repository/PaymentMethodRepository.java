package com.cakkie.backend.repository;

import com.cakkie.backend.model.address;
import com.cakkie.backend.model.paymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<paymentMethod, Integer> {
}
