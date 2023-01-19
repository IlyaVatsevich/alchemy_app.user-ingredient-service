package com.example.user_ingredient_service.validation.ingredient;

import com.example.user_ingredient_service.annotation.ingredient.ValidIfIngredientsExist;
import com.example.user_ingredient_service.repository.IngredientRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class IngredientsExistValidator implements ConstraintValidator<ValidIfIngredientsExist, List<Long>> {

    private final IngredientRepository ingredientRepository;

    @Override
    public boolean isValid(List<Long> value, ConstraintValidatorContext context) {

        if (value == null || value.isEmpty()) {
            return true;
        }

        return ingredientRepository.findAllById(value).size() == value.size();
    }
}
