package com.cakkie.backend.repository;

import com.cakkie.backend.model.shippingMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingMethodRepository extends JpaRepository<shippingMethod, Integer> {
}
