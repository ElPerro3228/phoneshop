package com.es.core.services;

import com.es.core.model.phone.Stock;
import com.es.core.model.phone.StockDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DefaultStockService implements StockService{

    @Resource
    private StockDao stockDao;


    @Override
    public void updateStock(Long phoneId, Integer deductionNumber) {
        Stock stock = stockDao.getStock(phoneId);
        stock.setStock(stock.getStock() - deductionNumber);
        stockDao.updateStock(stock);
    }
}
