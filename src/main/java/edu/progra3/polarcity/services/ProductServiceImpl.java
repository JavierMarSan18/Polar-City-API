package edu.progra3.polarcity.services;

import edu.progra3.polarcity.dto.ProductDTO;
import edu.progra3.polarcity.entities.Product;
import edu.progra3.polarcity.exceptions.ConflictException;
import edu.progra3.polarcity.exceptions.NotFoundException;
import edu.progra3.polarcity.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    public static final String NOT_FOUND_EXCEPTION = "Producto no encontrado.";
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ProductDTO> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapDTO).collect(Collectors.toList());
    }

    private ProductDTO mapDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_EXCEPTION));
        return mapDTO(product);
    }

    @Override
    public ProductDTO create(ProductDTO productDTO) {
        if (productRepository.existsById(productDTO.getId())) {
            throw new ConflictException("El producto ya existe");
        }
        Product newProduct = mapEntity(productDTO);

        return mapDTO(productRepository.save(newProduct));
    }

    private Product mapEntity(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    @Override
    public ProductDTO update(ProductDTO productDTO, Long id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException(NOT_FOUND_EXCEPTION);
        }
        Product updatedProduct = mapEntity(productDTO);
        updatedProduct.setId(id);
        return mapDTO(productRepository.save(updatedProduct));
    }

    @Override
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException(NOT_FOUND_EXCEPTION);
        }
        productRepository.deleteById(id);
    }
}
