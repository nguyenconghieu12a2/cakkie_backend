package com.cakkie.backend.repository;

import com.cakkie.backend.model.districts;
import com.cakkie.backend.model.provinces;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DistrictRepository extends CrudRepository<districts, String> {
    List<districts> findByProvinceCode(provinces provinceCode);
}
