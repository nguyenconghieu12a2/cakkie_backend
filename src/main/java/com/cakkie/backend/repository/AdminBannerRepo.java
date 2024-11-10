package com.cakkie.backend.repository;

import com.cakkie.backend.model.banners;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminBannerRepo extends JpaRepository<banners, Integer> {
    @Query(value = "select * from banners where is_deleted = 1", nativeQuery = true)
    List<banners> findAll();

    @Modifying
    @Query(value = "UPDATE banners SET is_deleted = 0 WHERE id = :id", nativeQuery = true)
    void deleteById(@Param("id") int id);
}
