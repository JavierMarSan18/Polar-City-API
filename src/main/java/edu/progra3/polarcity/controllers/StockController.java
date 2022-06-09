package edu.progra3.polarcity.controllers;

import edu.progra3.polarcity.dto.StockDTO;
import edu.progra3.polarcity.entities.Product;
import edu.progra3.polarcity.entities.Stock;
import edu.progra3.polarcity.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping()
    public List<Stock> findAll(){
        return stockService.finAll();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Stock> findByProductId(@PathVariable Long productId){
        return new ResponseEntity<>(stockService.findByProductId(productId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StockDTO> createStock(@RequestBody StockDTO stockDTO){
        return new ResponseEntity<>(stockService.createStock(stockDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Stock> updateStockByProductId(@RequestBody Stock stock, @PathVariable Long productId){
        return new ResponseEntity<>(stockService.updateStockByProductId(stock, productId), HttpStatus.OK);
    }
}
