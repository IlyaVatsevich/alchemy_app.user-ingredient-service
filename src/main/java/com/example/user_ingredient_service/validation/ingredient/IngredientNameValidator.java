package com.example.user_ingredient_service.validation.ingredient;

import com.example.user_ingredient_service.annotation.ingredient.ValidIngredientName;
import com.example.user_ingredient_service.repository.IngredientRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IngredientNameValidator implements ConstraintValidator<ValidIngredientName,String> {

    private final IngredientRepository ingredientRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null||value.isBlank()) {
            return true;
        }
        return !ingredientRepository.existsByName(value);
    }
}
