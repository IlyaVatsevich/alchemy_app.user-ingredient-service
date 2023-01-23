package com.example.user_ingredient_service.generator;

import com.example.user_ingredient_service.dto.ingredient.IngredientCreateDto;

import java.util.List;

import static com.example.user_ingredient_service.generator.SecondaryGeneratorUtil.*;

public class IngredientDtoGeneratorUtil {

    private IngredientDtoGeneratorUtil() {}

    public static IngredientCreateDto createValidIngredientCreateDto() {
        return IngredientCreateDto.builder()
                .withLossProbability(((short) 0))
                .withPrice(0)
                .withName(generateRndStr())
                .build();
    }

    public static IngredientCreateDto createRecipeIngredientDto() {
        return IngredientCreateDto.builder()
                .withLossProbability(generateShortForValidLossProbability())
                .withPrice(generatePositiveInteger())
                .withName(generateRndStr())
                .withIngredientIds(List.of(1000L,1200L))
                .build();
    }

    public static IngredientCreateDto createRecipeIngredientDtoWithNotExistingIngredients() {
        return IngredientCreateDto.builder()
                .withLossProbability(generateShortForValidLossProbability())
                .withPrice(generatePositiveInteger())
                .withName(generateRndStr())
                .withIngredientIds(List.of(1000L,2000L))
                .build();
    }

    public static IngredientCreateDto createIngredientWithInvalidLossProbability() {
        return IngredientCreateDto.builder()
                .withLossProbability(generateShortForInvalidLossProbability())
                .withPrice(0)
                .withName(generateRndStr())
                .build();
    }

    public static IngredientCreateDto createIngredientWithInvalidPrice() {
        return IngredientCreateDto.builder()
                .withLossProbability(generateShortForValidLossProbability())
                .withPrice(generateNegativeInteger())
                .withName(generateRndStr())
                .build();
    }

    public static IngredientCreateDto createBasicIngredientWithInvalidPriceAndLossProbability() {
        return IngredientCreateDto.builder()
                .withLossProbability(((short) 5))
                .withPrice(generatePositiveInteger())
                .withName(generateRndStr())
                .build();
    }

    public static IngredientCreateDto createIngredientWithIngredientsSizeEqualOne() {
        return IngredientCreateDto.builder()
                .withLossProbability(generateShortForValidLossProbability())
                .withPrice(generatePositiveInteger())
                .withName(generateRndStr())
                .withIngredientIds(List.of(1000L))
                .build();
    }

    public static IngredientCreateDto createIngredientWithAlreadyExistingName(String name) {
        return IngredientCreateDto.builder()
                .withLossProbability(((short) 0))
                .withPrice(0)
                .withName(name)
                .build();
    }
}
