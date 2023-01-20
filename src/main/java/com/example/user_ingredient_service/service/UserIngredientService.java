package com.example.user_ingredient_service.service;

import com.example.user_ingredient_service.annotation.pageable.ValidSortProperty;
import com.example.user_ingredient_service.dto.UserIngredientDto;
import com.example.user_ingredient_service.entity.Ingredient;
import com.example.user_ingredient_service.entity.User;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UserIngredientService {

    void addNewBasicIngredientToUsers(Ingredient ingredient);

    void initializeUserIngredient(User newUser);

    Page<UserIngredientDto> getUserIngredient(@Valid @ValidSortProperty(
            allowedProperties = {"count","ingredient.name","ingredient.id","ingredient.price","ingredient.lossProbability"}) Pageable pageable);

}
