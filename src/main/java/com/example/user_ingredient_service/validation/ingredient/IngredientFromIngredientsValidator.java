package com.example.user_ingredient_service.validation.ingredient;

import com.example.user_ingredient_service.annotation.ingredient.ValidIngredientFromIngredientsExist;
import com.example.user_ingredient_service.entity.Ingredient;
import com.example.user_ingredient_service.repository.IngredientRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class IngredientFromIngredientsValidator implements
        ConstraintValidator<ValidIngredientFromIngredientsExist, List<Long>> {

    private final IngredientRepository ingredientRepository;

    @Override
    public boolean isValid(List<Long> value, ConstraintValidatorContext context) {

        if (value == null || value.isEmpty()) {
            return true;
        }

        Optional<Ingredient> ingredientByIngredientsIds = ingredientRepository.findIngredientByIngredientsIds(value, ((long) value.size()));

        return ingredientByIngredientsIds.isEmpty();
    }
}
