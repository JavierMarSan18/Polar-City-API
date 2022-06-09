package edu.progra3.polarcity.repositories;

import edu.progra3.polarcity.entities.Product;
import edu.progra3.polarcity.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByProduct(Product product);
}
