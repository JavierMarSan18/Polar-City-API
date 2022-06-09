package edu.progra3.polarcity.repositories;

import edu.progra3.polarcity.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
