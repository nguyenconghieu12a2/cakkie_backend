package com.cakkie.backend.repository;

import com.cakkie.backend.model.districts;
import com.cakkie.backend.model.wards;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WardRepository extends CrudRepository<wards, String>{
    List<wards> findByDistrictCode(districts district);
}
