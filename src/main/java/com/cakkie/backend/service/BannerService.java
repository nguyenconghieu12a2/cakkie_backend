package com.cakkie.backend.service;

import com.cakkie.backend.model.banners;
import com.cakkie.backend.repository.BannerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerService {
    private BannerRepository bannerRepository;

    public BannerService(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    public List<banners> getActiveBanners() {
        return bannerRepository.findByIsDeleted(1);
    }
}
