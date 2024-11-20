package com.cakkie.backend.repository;

import com.cakkie.backend.model.shoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<shoppingCart, Integer> {

}
