package edu.progra3.polarcity.services;

import edu.progra3.polarcity.dto.ProductDTO;
import edu.progra3.polarcity.entities.Product;
import edu.progra3.polarcity.exceptions.ConflictException;
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

    @Override
    public ProductDTO create(ProductDTO productDTO) {
        if (productRepository.existsById(productDTO.getId())){
            throw new ConflictException("El producto ya existe");
        }
        Product newProduct= mapEntity(productDTO);

        return mapDto(productRepository.save(newProduct));
    }

    private ProductDTO mapDto(Product product){
        return modelMapper.map(product,ProductDTO.class);


    }
    private Product mapEntity(ProductDTO productDTO){
        return modelMapper.map(productDTO,Product.class);
    }
}
