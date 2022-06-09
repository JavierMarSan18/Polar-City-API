package edu.progra3.polarcity.services;

import edu.progra3.polarcity.dto.StockDTO;
import edu.progra3.polarcity.entities.Product;
import edu.progra3.polarcity.entities.Stock;
import edu.progra3.polarcity.exceptions.ConflictException;
import edu.progra3.polarcity.exceptions.NotFoundException;
import edu.progra3.polarcity.repositories.ProductRepository;
import edu.progra3.polarcity.repositories.StockRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class StockServiceImpl implements StockService{

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Stock> finAll() {
        return stockRepository.findAll();
    }

    @Override
    public Stock findByProductId(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Producto no encontrado"));
        return stockRepository.findByProduct(product).orElseThrow(() -> new NotFoundException("Stock no encontrado"));
    }

    @Override
    public StockDTO createStock(StockDTO stockDTO) {
        if(productRepository.existsById(stockDTO.getProduct().getId())){
            throw new ConflictException("Ya existe un inventario de este producto");
        }

        Product product = new Product();
        product.setId(stockDTO.getProduct().getId());
        product.setName(stockDTO.getProduct().getName());
        product.setPrice(stockDTO.getProduct().getPrice());
        productRepository.save(product);

        Stock stock = new Stock();
        stock.setProduct(entityManager.getReference(Product.class, product.getId()));
        stock.setQuantity(stockDTO.getQuantity());

        return mapDTO(stockRepository.save(stock));
    }

    @Override
    public Stock updateStockByProductId(Stock stock, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Producto no encontrado"));
        Stock currentStock = stockRepository.findByProduct(product).orElseThrow(() -> new NotFoundException("Stock no encontrado"));

        Stock updatedStock = new Stock();
        updatedStock.setId(currentStock.getId());
        updatedStock.setProduct(currentStock.getProduct());
        updatedStock.setQuantity(stock.getQuantity());

        return updatedStock;
    }

    private StockDTO mapDTO(Stock stock) {
        return modelMapper.map(stock, StockDTO.class);
    }
}
