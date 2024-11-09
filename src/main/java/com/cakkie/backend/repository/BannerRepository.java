package com.cakkie.backend.repository;

import com.cakkie.backend.model.banners;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BannerRepository extends CrudRepository<banners, Integer> {
    List<banners> findByIsDeleted(int isDeleted);
}
