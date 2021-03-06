package edu.progra3.polarcity.controllers;

import edu.progra3.polarcity.dto.ProductDTO;
import edu.progra3.polarcity.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    @CrossOrigin("*")
    public List<ProductDTO> findAll(){
        return productService.findAll();
    }

    @GetMapping("/{id}")
    @CrossOrigin("*")
    public ProductDTO findById(@PathVariable Long id){
        return productService.findById(id);
    }

    @PutMapping("/{id}")
    @CrossOrigin("*")
    public ResponseEntity <ProductDTO> update(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO){
        return new ResponseEntity<>(productService.update(productDTO,id), HttpStatus.ACCEPTED);
    }
}
