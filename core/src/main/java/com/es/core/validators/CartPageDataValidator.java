package com.es.core.validators;

import com.es.core.cart.CartItem;
import com.es.core.cart.CartPageData;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Map;

public class CartPageDataValidator implements ConstraintValidator<CorrectQuantities, CartPageData> {

   @Autowired
   private QuantityValidator quantityValidator;

   public void initialize(CorrectQuantities constraint) {
   }

   public boolean isValid(CartPageData cartPageData, ConstraintValidatorContext context) {
      List<CartItem> cartItems = cartPageData.getCartItems();
      Map<Long, String> errorsMap = cartPageData.getErrorsMap();
      boolean isValid = true;
      for (CartItem cartItem : cartItems) {
         if (cartItem.getQuantity() < 1) {
            isValid = false;
            errorsMap.put(cartItem.getPhoneId(), "must be more than 1 ");
         }
         if (!quantityValidator.isValid(cartItem.getPhoneId(), cartItem.getQuantity())) {
            isValid = false;
            errorsMap.put(cartItem.getPhoneId(), "invalid quantity ");
         }
      }
      return isValid;
   }
}
