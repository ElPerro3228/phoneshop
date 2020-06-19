package com.es.core.validators;

import com.es.core.cart.CartPageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Map;

@Service
public class CartPageDataValidator implements Validator {

   private QuantityValidator quantityValidator;

   @Autowired
   public void setQuantityValidator(QuantityValidator quantityValidator) {
      this.quantityValidator = quantityValidator;
   }

   @Override
   public boolean supports(Class<?> aClass) {
      return CartPageData.class.isAssignableFrom(aClass);
   }

   @Override
   public void validate(Object o, Errors errors) {
      CartPageData cartPageData = (CartPageData) o;
      Map<Long, Long> cartItems = cartPageData.getCartItems();
      for (Map.Entry<Long, Long> entry : cartItems.entrySet()) {
         if (entry.getValue() < 1) {
            errors.rejectValue("cartItems[" + entry.getKey().toString() + "]", "validation.moreThanOne", "must be more than 1");
         }
         if (!quantityValidator.isValid(entry.getKey(), entry.getValue())) {
            errors.rejectValue("cartItems[" + entry.getKey().toString() + "]", "validation.outOfStock", "Out of stock");
         }
      }
   }
}
