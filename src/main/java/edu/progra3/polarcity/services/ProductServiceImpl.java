package edu.progra3.polarcity.services;

import edu.progra3.polarcity.dto.ProductDTO;
import edu.progra3.polarcity.entities.Product;
import edu.progra3.polarcity.exceptions.ConflictException;
import edu.progra3.polarcity.exceptions.NotFoundException;
import edu.progra3.polarcity.repositories.ProductRepository;
import edu.progra3.polarcity.utils.MapUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private MapUtil mapUtil;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductDTO> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> mapUtil.mapDTO(product)).collect(Collectors.toList());
    }

    @Override
    public ProductDTO findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Producto no encontrado"));
        return mapUtil.mapDTO(product);
    }

    @Override
    public ProductDTO create(ProductDTO productDTO) {
        if (productRepository.existsById(productDTO.getId())) {
            throw new ConflictException("El producto ya existe");
        }
        Product newProduct = mapUtil.mapEntity(productDTO);

        return mapUtil.mapDTO(productRepository.save(newProduct));
    }

    @Override
    public ProductDTO update(ProductDTO productDTO, Long id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException("El producto no existe.");
        }
        Product updatedProduct = mapUtil.mapEntity(productDTO);
        updatedProduct.setId(id);
        return mapUtil.mapDTO(productRepository.save(updatedProduct));
    }

    @Override
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException("Producto no encontrado");
        }
        productRepository.deleteById(id);
    }
}
