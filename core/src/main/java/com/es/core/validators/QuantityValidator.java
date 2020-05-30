package com.es.core.validators;

import com.es.core.model.phone.StockDao;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class QuantityValidator implements ConstraintValidator<QuantityConstraint, Object> {

    private StockDao stockDao;
    @Autowired
    public void setStockDao(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    private String quantity;
    private String phoneId;

    @Override
    public void initialize(QuantityConstraint constraintAnnotation) {
        this.phoneId = constraintAnnotation.phoneId();
        this.quantity = constraintAnnotation.quantity();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        long quantityValue = (long) new BeanWrapperImpl(value)
                .getPropertyValue(quantity);
        long phoneIdValue = (long) new BeanWrapperImpl(value)
                .getPropertyValue(phoneId);
        int stock = stockDao.getStock(phoneIdValue);
        return quantityValue < stock;
    }
}
