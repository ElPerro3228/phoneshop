package com.es.core.validators;

import com.es.core.cart.CartDTO;
import com.es.core.model.phone.Phone;
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
      return CartDTO.class.isAssignableFrom(aClass);
   }

   @Override
   public void validate(Object o, Errors errors) {
      CartDTO cartDTO = (CartDTO) o;
      Map<Phone, Long> cartItems = cartDTO.getCartItems();
      for (Map.Entry<Phone, Long> entry : cartItems.entrySet()) {
         if (entry.getValue() < 0) {
            errors.rejectValue("cartItems[" + entry.getKey().getId() + "]", "validation.cartpage.quantity", "Must be more or equal to 0");
         }
         if (!quantityValidator.isValid(entry.getKey().getId(), entry.getValue())) {
            errors.rejectValue("cartItems[" + entry.getKey().getId() + "]", "validation.outOfStock", "Out of stock");
         }
      }
   }
}
