package com.cakkie.backend.repository.customerRV;

import com.cakkie.backend.model.orderLine;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderLineReviewRepo extends JpaRepository<orderLine, Integer> {
}
