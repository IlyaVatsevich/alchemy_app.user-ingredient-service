package com.example.user_ingredient_service.validation.ingredient;

import com.example.user_ingredient_service.annotation.ingredient.ValidIngredientsBasicOrRecipeSize;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class IngredientsBasicOrRecipeSizeValidator implements ConstraintValidator<ValidIngredientsBasicOrRecipeSize, List<Long>> {

    @Override
    public boolean isValid(List<Long> value, ConstraintValidatorContext context) {

        if (value == null || value.isEmpty()) {
            return true;
        }

        return value.size()!=1;
    }
}
