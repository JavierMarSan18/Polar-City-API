package edu.progra3.polarcity.services;


import edu.progra3.polarcity.dto.OrderDTO;
import java.util.Queue;

public interface OrderService {

    Queue<OrderDTO> findAll();
    Queue<OrderDTO> findAllByStatus(String status);
    OrderDTO findById(Long id);
    OrderDTO generateOrder(OrderDTO orderDTO);
    OrderDTO dispatchOrder(Long id);
    String deleteOrderById(Long id);
}
