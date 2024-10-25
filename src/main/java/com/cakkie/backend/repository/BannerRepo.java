package com.cakkie.backend.repository;

import com.cakkie.backend.model.banners;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepo extends JpaRepository<banners, Integer> {
}
