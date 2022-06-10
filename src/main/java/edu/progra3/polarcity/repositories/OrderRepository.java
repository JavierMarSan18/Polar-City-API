package edu.progra3.polarcity.repositories;

import edu.progra3.polarcity.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    LinkedList<Order> findAll();
    List<Order> findAllByClient(String client);
}
