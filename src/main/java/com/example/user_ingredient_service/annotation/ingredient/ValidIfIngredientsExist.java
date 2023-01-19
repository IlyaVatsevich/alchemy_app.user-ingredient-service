package com.example.user_ingredient_service.annotation.ingredient;

import com.example.user_ingredient_service.validation.ingredient.IngredientsExistValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IngredientsExistValidator.class)
public @interface ValidIfIngredientsExist {

    String message() default "Ingredients not exist.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
