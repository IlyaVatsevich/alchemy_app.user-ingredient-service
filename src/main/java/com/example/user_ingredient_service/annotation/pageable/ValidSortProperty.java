package com.example.user_ingredient_service.annotation.pageable;

import com.example.user_ingredient_service.validation.pageable.SortPropertyValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SortPropertyValidator.class)
public @interface ValidSortProperty {

    String message() default "You can't sort by not existing property.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] allowedProperties() default {};

}
