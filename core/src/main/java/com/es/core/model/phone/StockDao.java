package com.es.core.model.phone;

public interface StockDao {
    Stock getStock(Long phoneId);
    void updateStock(Stock stock);
}
