package com.cakkie.backend.service;

import com.cakkie.backend.dto.AdminOOSProductDTO;
import com.cakkie.backend.repository.AdminOOSProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AdminOOSProductService {
    private final AdminOOSProductRepo adminOOSProductRepo;

    public List<AdminOOSProductDTO> getOOSProducts() {
        List<Object[]> oosProducts = adminOOSProductRepo.getOOSProduct();
        List<AdminOOSProductDTO> oosProductDTOList = new ArrayList<>();
        NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));

        for (Object[] row : oosProducts) {
            int proiId = ((Number) row[0]).intValue();
            String proName = (String) row[1];
            String size = (String) row[2];
            String proImage = (String) row[3];
            int quantity = ((Number) row[4]).intValue();
            Long price = ((Number) row[5]).longValue();
            String formattedPrice = currencyFormat.format(price) + " VND";

            AdminOOSProductDTO oosProductDTO = new AdminOOSProductDTO(
                    proiId, proName, size, proImage, quantity, formattedPrice
            );
            oosProductDTOList.add(oosProductDTO);
        }
        return oosProductDTOList;
    }
}
