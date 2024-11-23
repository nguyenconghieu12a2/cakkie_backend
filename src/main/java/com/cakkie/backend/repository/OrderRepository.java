package com.cakkie.backend.repository;

import com.cakkie.backend.model.shopOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<shopOrder, Integer> {
}
