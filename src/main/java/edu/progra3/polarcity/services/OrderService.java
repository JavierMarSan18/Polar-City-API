package edu.progra3.polarcity.services;


import edu.progra3.polarcity.dto.OrderDTO;
import edu.progra3.polarcity.dto.ProductOrderDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Queue;

public interface OrderService {

    Queue<OrderDTO> findAll();
    OrderDTO generateOrder(List<ProductOrderDTO> productOrderDTOS, String clientName);
    OrderDTO findById(Long id);
}
