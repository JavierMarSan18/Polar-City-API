package edu.progra3.polarcity.controllers;

import edu.progra3.polarcity.dto.OrderDTO;
import edu.progra3.polarcity.entities.Order;
import edu.progra3.polarcity.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{id}")
    public OrderDTO findById(@PathVariable Long id){
        return orderService.findById(id);
    }

}
