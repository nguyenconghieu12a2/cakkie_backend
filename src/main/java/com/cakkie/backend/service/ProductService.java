package com.cakkie.backend.service;

import com.cakkie.backend.dto.ProductDTO;
import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();
}
