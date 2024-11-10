package com.cakkie.backend.repository;

import com.cakkie.backend.model.productDesTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductDesTitleRepository extends JpaRepository<productDesTitle, Integer> {
    @Query(value = "SELECT pdt.des_title_id, pdt.des_title_name, pdt.is_deleted FROM product_des_title pdt WHERE pdt.des_title_id = ?1", nativeQuery = true)
    Optional<productDesTitle> findById(int desTitleID);

    @Query(value = "SELECT des_title_name FROM product_des_title", nativeQuery = true)
    List<String> getAllDesTitle();
}
