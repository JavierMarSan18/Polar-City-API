package edu.progra3.polarcity.services;


import edu.progra3.polarcity.dto.OrderDTO;
import edu.progra3.polarcity.dto.ProductOrderDTO;
import edu.progra3.polarcity.entities.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Queue;

public interface OrderService {

    Queue<OrderDTO> findAll();
    Queue<OrderDTO> findAllByStatus(String status);
    OrderDTO findById(Long id);
    List<OrderDTO> findAllByClient(String clientName);
    OrderDTO generateOrder(OrderDTO orderDTO);
    OrderDTO dispatchOrder(Long id);
    String deleteOrderById(Long id);
}
