package edu.progra3.polarcity.services;


import edu.progra3.polarcity.dto.ProductOrderDTO;
import edu.progra3.polarcity.entities.Order;

import java.util.List;

public interface OrderService {
    Order generateOrder(Long clientId, List<ProductOrderDTO> productOrders);
}
