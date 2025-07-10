package com.dev.laedson.simple_auth_api.service;

import com.dev.laedson.simple_auth_api.DTO.CreateStockDTO;
import com.dev.laedson.simple_auth_api.entity.Stock;
import com.dev.laedson.simple_auth_api.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void createStock(CreateStockDTO createStockDTO) {

        var stock = new Stock(
                createStockDTO.stockId(),
                createStockDTO.descripiton()
        );

        stockRepository.save(stock);
    }
}
