package com.cakkie.backend.service.adminOthers;

import com.cakkie.backend.dto.adminOthers.BestSellerProductDTO;
import com.cakkie.backend.repository.adminOthers.BestSellerProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class BestSellerProductService {
    private final BestSellerProductRepo bestSellerProductRepo;

    public List<BestSellerProductDTO> getBestSellerProducts() {
        List<Object[]> bestSeller = bestSellerProductRepo.getBestSellerProduct();
        List<BestSellerProductDTO> bestSellerProductDTOList = new ArrayList<>();
        NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));

        for (Object[] row : bestSeller) {
            int proId = ((Number) row[0]).intValue();
            String productName = (String) row[1];
            String productImage = (String) row[2];
            int rating = ((Number) row[3]).intValue();
            Long price = ((Number) row[4]).longValue();
            String formattedPrice = currencyFormat.format(price) + " VND";

            BestSellerProductDTO bestSellerProductDTO = new BestSellerProductDTO(
                    proId, productName, productImage, rating, formattedPrice
            );
            bestSellerProductDTOList.add(bestSellerProductDTO);
        }
        return bestSellerProductDTOList;
    }
}
