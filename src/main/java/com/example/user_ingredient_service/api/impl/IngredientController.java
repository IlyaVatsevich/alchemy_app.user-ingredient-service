package com.example.user_ingredient_service.api.impl;

import com.example.user_ingredient_service.api.IngredientApiDescription;
import com.example.user_ingredient_service.dto.ingredient.IngredientCreateDto;
import com.example.user_ingredient_service.dto.ingredient.IngredientResponseDto;
import com.example.user_ingredient_service.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IngredientController implements IngredientApiDescription {

    private final IngredientService ingredientService;

    @Override
    public ResponseEntity<IngredientCreateDto> createIngredient(IngredientCreateDto ingredientDto) {
        ingredientService.createIngredient(ingredientDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredientDto);
    }

    @Override
    public ResponseEntity<Page<IngredientResponseDto>> getAllIngredients(Pageable pageable) {
        return ResponseEntity.ok(ingredientService.getAllIngredients(pageable));
    }

    @Override
    public ResponseEntity<IngredientResponseDto> getIngredient(Long id) {
        return ResponseEntity.ok(ingredientService.getIngredientById(id));
    }

}
