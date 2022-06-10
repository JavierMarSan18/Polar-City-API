package edu.progra3.polarcity.repositories;

import edu.progra3.polarcity.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.LinkedList;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    LinkedList<Order> findAll();
}
