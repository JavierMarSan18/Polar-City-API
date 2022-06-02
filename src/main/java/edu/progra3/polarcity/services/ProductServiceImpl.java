package edu.progra3.polarcity.services;

import edu.progra3.polarcity.dto.ProductDTO;
import edu.progra3.polarcity.entities.Product;
import edu.progra3.polarcity.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductRepository productRepository;
    @Override
    public List<ProductDTO> findAll() {
        List<Product> products= productRepository.findAll();
        return products.stream().map(this::mapDto).collect(Collectors.toList());
    }
    private ProductDTO mapDto(Product product){
        return modelMapper.map(product,ProductDTO.class);
    }
}
