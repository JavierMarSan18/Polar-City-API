package edu.progra3.polarcity.repositories;

import edu.progra3.polarcity.entities.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
}
