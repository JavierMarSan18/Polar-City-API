package edu.progra3.polarcity.services;

import edu.progra3.polarcity.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> findAll();
    ProductDTO create(ProductDTO productDTO);
    ProductDTO update(ProductDTO productDTO, Long id);
    void delete(Long id);
}

