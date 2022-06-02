package edu.progra3.polarcity.services;

import edu.progra3.polarcity.dto.ProductDTO;
import edu.progra3.polarcity.entities.Product;

import java.util.List;

public interface ProductService {
    List<ProductDTO> findAll();
    ProductDTO findById(Long id);
    ProductDTO create(ProductDTO productDTO);
    ProductDTO update(ProductDTO productDTO, Long id);
    void delete(Long id);
}

