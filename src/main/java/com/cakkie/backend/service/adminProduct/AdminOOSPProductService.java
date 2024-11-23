package com.cakkie.backend.service.adminProduct;

import com.cakkie.backend.dto.adminProduct.AdminOOSProductDTO;
import com.cakkie.backend.model.productItem;
import com.cakkie.backend.repository.adminProduct.AdminOOSProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminOOSPProductService {
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

    public productItem updateQuantity(int productItemId, long quantity) {
        Optional<productItem> existingProduct = adminOOSProductRepo.findById(productItemId);
        if (!existingProduct.isPresent()) {
            throw new IllegalArgumentException("Product does not exist with id: " + productItemId);
        }

        productItem productItem = existingProduct.get();
        productItem.setQtyInStock(quantity);

        return adminOOSProductRepo.save(productItem);
    }
}
