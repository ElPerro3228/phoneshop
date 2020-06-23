package com.es.core.validators;

import com.es.core.cart.CartPageDTO;
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
      return CartPageDTO.class.isAssignableFrom(aClass);
   }

   @Override
   public void validate(Object o, Errors errors) {
      CartPageDTO cartPageDTO = (CartPageDTO) o;
      Map<Long, Long> cartItems = cartPageDTO.getCartItems();
      for (Map.Entry<Long, Long> entry : cartItems.entrySet()) {
         if (entry.getValue() < 0) {
            errors.rejectValue("cartItems[" + entry.getKey().toString() + "]", "validation.cartpage.quantity", "Must be more or equal to 0");
         }
         if (!quantityValidator.isValid(entry.getKey(), entry.getValue())) {
            errors.rejectValue("cartItems[" + entry.getKey().toString() + "]", "validation.outOfStock", "Out of stock");
         }
      }
   }
}
