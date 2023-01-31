package com.example.user_ingredient_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@AllArgsConstructor
@Builder(setterPrefix = "with")
@Jacksonized
@Getter
public class IngredientNotification {

    private IngredientForNotification ingredient;

    private String mail;

}
