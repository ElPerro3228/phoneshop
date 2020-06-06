package com.es.core.validators;

import com.es.core.cart.CartItem;
import com.es.core.model.phone.Stock;
import com.es.core.model.phone.StockDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuantityValidator {

    private StockDao stockDao;

    @Autowired
    public void setStockDao(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    public boolean isValid(Long phoneId, Long quantity) {
        Stock stock = stockDao.getStock(phoneId);
        return quantity < stock.getStock();
    }
}
