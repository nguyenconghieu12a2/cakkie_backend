package com.cakkie.backend.repository;

import com.cakkie.backend.model.address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<address, Integer> {
}
