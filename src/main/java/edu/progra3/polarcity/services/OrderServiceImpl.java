package edu.progra3.polarcity.services;

import edu.progra3.polarcity.dto.ProductOrderDTO;
import edu.progra3.polarcity.entities.Client;
import edu.progra3.polarcity.entities.Order;
import edu.progra3.polarcity.exceptions.NotFoundException;
import edu.progra3.polarcity.repositories.ClientRepository;
import edu.progra3.polarcity.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OrderServiceImpl implements OrderService{

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Order generateOrder(Long clientId, List<ProductOrderDTO> productOrders) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        Order order = new Order();
        order.setSoldOut(false);

        return null;
    }
}
