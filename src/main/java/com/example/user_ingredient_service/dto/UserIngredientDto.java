package com.example.user_ingredient_service.dto;

import com.example.user_ingredient_service.dto.ingredient.IngredientResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Builder(setterPrefix = "with")
@Getter
@Jacksonized
@AllArgsConstructor
public class UserIngredientDto {

    private IngredientResponseDto ingredient;

    private Long count;
}
