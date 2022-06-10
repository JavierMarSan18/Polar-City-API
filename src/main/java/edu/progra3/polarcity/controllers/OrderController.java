package edu.progra3.polarcity.controllers;

import edu.progra3.polarcity.dto.OrderDTO;
import edu.progra3.polarcity.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/status")
    public Queue<OrderDTO> findAllByStatus(@RequestParam(name = "name") String status){
        return orderService.findAllByStatus(status);
    }

    @GetMapping("/{id}")
    public OrderDTO findById(@PathVariable Long id){
        return orderService.findById(id);
    }


    @PostMapping()
    public ResponseEntity<OrderDTO> generateOrder(@Valid @RequestBody OrderDTO orderDTO){
        return new ResponseEntity<>(orderService.generateOrder(orderDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> dispatchOrder(@PathVariable Long id){
        return new ResponseEntity<>(orderService.dispatchOrder(id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderById(@PathVariable Long id){
        return new ResponseEntity<>(orderService.deleteOrderById(id), HttpStatus.ACCEPTED);
    }
}
