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
@RequestMapping("/api/inventory")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductDTO> findAll(){
        return productService.findAll();
    }

    @PostMapping
    public ResponseEntity <ProductDTO> create( @Valid @RequestBody ProductDTO productDTO){
        return new ResponseEntity<>(productService.create(productDTO), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity <ProductDTO> update(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO){
        return new ResponseEntity<>(productService.update(productDTO,id), HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        productService.delete(id);
    }
}
