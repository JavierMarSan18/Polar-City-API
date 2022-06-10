package edu.progra3.polarcity.controllers;

import edu.progra3.polarcity.dto.OrderDTO;
import edu.progra3.polarcity.dto.ProductOrderDTO;
import edu.progra3.polarcity.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Queue;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public Queue<OrderDTO> findAll(){
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public OrderDTO findById(@PathVariable Long id){
        return orderService.findById(id);
    }

    @GetMapping("/client")
    public List<OrderDTO> findByAllClient(@RequestParam("name") String client){
        return orderService.findAllByClient(client);
    }

    @PostMapping("/client")
    public ResponseEntity<OrderDTO> generateOrder(@RequestBody List<ProductOrderDTO> productOrderDTOS, @RequestParam("name") String client){
        return new ResponseEntity<>(orderService.generateOrder(productOrderDTOS,client), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateById(@PathVariable Long id, @RequestBody OrderDTO orderDTO){
        return new ResponseEntity<>(orderService.updateOrder(id, orderDTO), HttpStatus.ACCEPTED);
    }
}
