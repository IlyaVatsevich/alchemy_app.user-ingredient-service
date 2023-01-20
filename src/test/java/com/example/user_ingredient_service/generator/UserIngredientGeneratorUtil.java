package com.example.user_ingredient_service.generator;


import com.example.user_ingredient_service.entity.Ingredient;
import com.example.user_ingredient_service.entity.User;

public class UserIngredientGeneratorUtil {

    private UserIngredientGeneratorUtil(){}

    public static User.UserIngredient createUserIngredient(Ingredient ingredient, Long count) {
        return User.UserIngredient.builder()
                .withIngredient(ingredient)
                .withCount(count)
                .build();
    }
}
