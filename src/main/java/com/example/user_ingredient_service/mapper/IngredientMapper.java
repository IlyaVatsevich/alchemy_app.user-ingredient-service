package com.example.user_ingredient_service.mapper;

import com.example.user_ingredient_service.dto.ingredient.IngredientCreateDto;
import com.example.user_ingredient_service.dto.ingredient.IngredientResponseDto;
import com.example.user_ingredient_service.entity.Ingredient;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class IngredientMapper {

    public Ingredient fromDtoToEntity(IngredientCreateDto ingredientDto) {
        return Ingredient.builder()
                .withName(ingredientDto.getName())
                .withPrice(ingredientDto.getPrice())
                .withLossProbability(ingredientDto.getLossProbability())
                .build();
    }

    public IngredientResponseDto fromEntityToDto(Ingredient ingredient) {
        return IngredientResponseDto.builder()
                .withId(ingredient.getId())
                .withIngredients(mapIngredientDtos(ingredient.getIngredients()))
                .withName(ingredient.getName())
                .withPrice(ingredient.getPrice())
                .withLossProbability(ingredient.getLossProbability())
                .build();
    }

    private List<IngredientResponseDto> mapIngredientDtos(List<Ingredient.IngredientsCreatedFrom> ingredients) {
        return ingredients.stream()
                .map(ingredientsCreatedFrom -> fromEntityToDto(ingredientsCreatedFrom.getIngredient()))
                .toList();
    }

    public List<Ingredient.IngredientsCreatedFrom> mapIngredientsToIngredientsCreatedFrom(List<Ingredient> ingredients) {
        return ingredients.stream()
                .map(this::mapIngredientToIngredientCreatedFrom)
                .toList();
    }

    private Ingredient.IngredientsCreatedFrom mapIngredientToIngredientCreatedFrom(Ingredient ingredient) {
        return Ingredient.IngredientsCreatedFrom.builder()
                .withIngredient(ingredient)
                .build();
    }
}
