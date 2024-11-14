package com.cakkie.backend.repository;

import com.cakkie.backend.model.address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AddressRepository extends JpaRepository<address, Integer> {

}
