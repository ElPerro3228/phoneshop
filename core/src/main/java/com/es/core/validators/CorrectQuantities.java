package com.es.core.validators;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CartPageDataValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CorrectQuantities {
    String message() default "Fields values don't match!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
