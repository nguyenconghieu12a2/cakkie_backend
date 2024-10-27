package com.cakkie.backend.service;

import com.cakkie.backend.DTO.ProductDTO;
import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();
}
