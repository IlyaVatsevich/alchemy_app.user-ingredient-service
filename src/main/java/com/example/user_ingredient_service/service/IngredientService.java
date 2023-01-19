package com.example.user_ingredient_service.service;

import com.example.user_ingredient_service.annotation.pageable.ValidSortProperty;
import com.example.user_ingredient_service.dto.ingredient.IngredientCreateDto;
import com.example.user_ingredient_service.dto.ingredient.IngredientResponseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

@Validated
public interface IngredientService {

    void createIngredient(@Valid IngredientCreateDto ingredientDto);

    Page<IngredientResponseDto> getAllIngredients(@Valid @ValidSortProperty(
            allowedProperties = {"name","id","price","lossProbability"}) Pageable pageable);

    IngredientResponseDto getIngredientById(Long id);


}
