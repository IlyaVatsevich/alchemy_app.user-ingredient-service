package com.example.user_ingredient_service.annotation.ingredient;

import com.example.user_ingredient_service.validation.ingredient.IngredientsBasicOrRecipeSizeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IngredientsBasicOrRecipeSizeValidator.class)
public @interface ValidIngredientsBasicOrRecipeSize {

    String message() default "Ingredients size not correct.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
