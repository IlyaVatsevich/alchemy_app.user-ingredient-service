package com.example.user_ingredient_service.mapper;

import com.example.user_ingredient_service.dto.UserIngredientDto;
import com.example.user_ingredient_service.entity.Ingredient;
import com.example.user_ingredient_service.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserIngredientMapper {

    private final IngredientMapper ingredientMapper;

    public List<User.UserIngredient> buildUserIngredients(Collection<Ingredient> ingredients) {
        return ingredients.stream()
                .map(this::buildUserIngredient)
                .toList();
    }

    public User.UserIngredient buildUserIngredient(Ingredient ingredient) {
        return User.UserIngredient.builder()
                .withIngredient(ingredient)
                .withCount(1L)
                .build();
    }

    public UserIngredientDto userIngredientToUserIngredientDto(User.UserIngredient userIngredient) {
        return UserIngredientDto.builder()
                .withIngredient(ingredientMapper.fromEntityToDto(userIngredient.getIngredient()))
                .withCount(userIngredient.getCount())
                .build();
    }

}
