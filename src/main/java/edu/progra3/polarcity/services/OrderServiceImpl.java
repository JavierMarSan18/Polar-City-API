package edu.progra3.polarcity.services;

import edu.progra3.polarcity.dto.OrderDTO;
import edu.progra3.polarcity.dto.ProductOrderDTO;
import edu.progra3.polarcity.entities.Order;
import edu.progra3.polarcity.entities.ProductOrder;
import edu.progra3.polarcity.exceptions.NotFoundException;
import edu.progra3.polarcity.repositories.OrderRepository;
import edu.progra3.polarcity.utils.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MapUtil mapUtil;


    @Override
    public Order generateOrder(List<ProductOrderDTO> productOrders) {


        return null;
    }

    @Override
    public OrderDTO findById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Orden no encontrada."));
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setDate(order.getDate());

//        Set<ProductOrderDTO> products = order.getProducts().stream().map(this::mapProductOrderDTO).collect(Collectors.toSet());

//        orderDTO.setProducts(products);

        return orderDTO;
    }

    private ProductOrderDTO mapProductOrderDTO(ProductOrder productOrder)
    {
        ProductOrderDTO productOrderDTO = new ProductOrderDTO();
        productOrderDTO.setId(productOrder.getId());
//        productOrderDTO.setProduct(mapUtil.mapDTO(productOrder.getProduct()));
        productOrderDTO.setQuantity(productOrder.getQuantity());

        return productOrderDTO;
    }
}
