package com.cakkie.backend.repository;

import com.cakkie.backend.model.coupons;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CouponRepository extends CrudRepository<coupons, Integer>{
    List<coupons> findByIsDeleted(int isDeleted);
}
