package edu.progra3.polarcity.services;

import edu.progra3.polarcity.dto.StockDTO;
import edu.progra3.polarcity.entities.Stock;

import java.util.List;

public interface StockService {
    List<Stock> finAll();
    StockDTO findByProductId(Long productId);
    StockDTO createStock(StockDTO stockDTO);
    StockDTO updateStockByProductId(StockDTO stockDTO, Long productId);
}
