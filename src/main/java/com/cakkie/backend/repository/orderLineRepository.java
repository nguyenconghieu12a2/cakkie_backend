package com.cakkie.backend.repository;

import com.cakkie.backend.model.orderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface orderLineRepository extends JpaRepository<orderLine, Integer> {
}
