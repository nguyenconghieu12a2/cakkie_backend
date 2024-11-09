package com.cakkie.backend.repository;

import com.cakkie.backend.model.product;
import com.cakkie.backend.model.productDesInfo;
import com.cakkie.backend.model.productDesTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductDesTitleRepository extends JpaRepository<productDesTitle, Integer> {
}
