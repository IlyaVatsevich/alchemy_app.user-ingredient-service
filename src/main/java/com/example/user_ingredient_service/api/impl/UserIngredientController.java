package com.example.user_ingredient_service.api.impl;

import com.example.user_ingredient_service.api.UserIngredientApiDescription;
import com.example.user_ingredient_service.dto.UserIngredientDto;
import com.example.user_ingredient_service.service.UserIngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserIngredientController implements UserIngredientApiDescription {

    private final UserIngredientService userIngredientService;

    @Override
    public ResponseEntity<Page<UserIngredientDto>> getUserIngredients(Pageable pageable) {
        return ResponseEntity.ok(userIngredientService.getUserIngredient(pageable));
    }
}
