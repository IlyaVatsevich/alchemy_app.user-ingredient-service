package com.example.user_ingredient_service.generator;

import com.example.user_ingredient_service.entity.Ingredient;

import static com.example.user_ingredient_service.generator.SecondaryGeneratorUtil.generateRndStr;

public class IngredientGeneratorUtil {

    private IngredientGeneratorUtil() {}

    public static Ingredient createValidIngredient() {
        return Ingredient.builder()
                .withPrice(100)
                .withName(generateRndStr())
                .withLossProbability(((short) 5))
                .build();
    }

}
