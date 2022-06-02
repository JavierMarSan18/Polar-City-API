package edu.progra3.polarcity.repositories;

import edu.progra3.polarcity.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {

}
