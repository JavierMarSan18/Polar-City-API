package edu.progra3.polarcity.controllers;

import edu.progra3.polarcity.dto.ProductDTO;
import edu.progra3.polarcity.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductDTO> findAll(){
        return productService.findAll();
    }
}
