package com.es.core.model.phone;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
public class JdbcStockDaoTest extends AbstractIntegrationTest {

    private static final Long PHONE_ID = 1001L;

    @Autowired
    private StockDao stockDao;

    @Test
    public void shouldGetStock() {
        Stock stock = stockDao.getStock(PHONE_ID);
        assertThat(stock.getPhone()).isNotNull();
        assertThat(stock.getStock())
                .isNotNull()
                .isGreaterThan(0);
    }

    @Test
    public void shouldUpdateStock() {
        Stock stock = stockDao.getStock(PHONE_ID);
        int oldStock = stock.getStock();
        stock.setStock(stock.getStock() - 2);
        stockDao.updateStock(stock);
        Stock stock1 = stockDao.getStock(PHONE_ID);
        assertThat(stock1.getStock())
                .isNotNull()
                .isEqualTo(oldStock - 2);
    }
}
