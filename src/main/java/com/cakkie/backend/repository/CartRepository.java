package com.cakkie.backend.repository;

import com.cakkie.backend.dto.CartDTO;
import com.cakkie.backend.model.productCart;
import com.cakkie.backend.model.shoppingCart;
import com.cakkie.backend.model.shoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CartRepository extends JpaRepository<shoppingCartItem, Integer> {

//    @Query(value = "SELECT pi.id " +
//            "FROM productItem pi " +
//            "JOIN pi.shoppingCartItemsList pc " +
//            "WHERE pi.id = :id")
//    shoppingCartItem getproductCartById(@Param("id") Integer id);

    @Query("SELECT pc FROM shoppingCartItem pc WHERE pc.id = :id")
    shoppingCartItem getProductCartById(@Param("id") Integer id);
}
