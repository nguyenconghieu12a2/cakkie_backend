package com.cakkie.backend.repository;

import com.cakkie.backend.model.productItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductItemRepository extends JpaRepository<productItem, Integer> {

}
