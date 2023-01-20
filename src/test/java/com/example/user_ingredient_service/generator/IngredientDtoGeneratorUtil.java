package com.example.user_ingredient_service.generator;

import com.example.user_ingredient_service.dto.ingredient.IngredientCreateDto;

import java.util.List;

public class IngredientDtoGeneratorUtil {

    private IngredientDtoGeneratorUtil() {}

    public static IngredientCreateDto createRecipeIngredientDto() {
        return IngredientCreateDto.builder().
                withLossProbability(SecondaryGeneratorUtil.generateShortForValidLossProbability()).
                withPrice(SecondaryGeneratorUtil.generatePositiveInteger()).
                withName(SecondaryGeneratorUtil.generateRndStr()).
                withIngredientIds(List.of(1000L,1100L)).
                build();
    }



}
