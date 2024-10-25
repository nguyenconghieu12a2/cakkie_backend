package com.cakkie.backend.service;

import com.cakkie.backend.exception.BannerNotFoundException;
import com.cakkie.backend.model.banners;
import com.cakkie.backend.repository.BannerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BannersService {
    private final BannerRepo bannerRepo;

    public List<banners> getAllBanners() {
        return bannerRepo.findAll();
    }

    public banners updateBanners(banners banners, int id) {
        return bannerRepo.findById(id).map(st -> {
            st.setTitle(banners.getTitle());
            st.setImage(banners.getImage());
            st.setLink(banners.getLink());
            return bannerRepo.save(st);
        }).orElseThrow(() -> new BannerNotFoundException("Banner not found"));
    }

    public banners addBanners(banners banners) {
        return bannerRepo.save(banners);
    }

    public void deleteBanners(int id) {
        if(!bannerRepo.existsById(id)) {
            throw new BannerNotFoundException("Sorry, Banner not found!");
        }
    }
}
