package com.example.user_ingredient_service.dto.ingredient;

import com.example.user_ingredient_service.annotation.ingredient.ValidIfIngredientsExist;
import com.example.user_ingredient_service.annotation.ingredient.ValidIngredientFromIngredientsExist;
import com.example.user_ingredient_service.annotation.ingredient.ValidIngredientName;
import com.example.user_ingredient_service.annotation.ingredient.ValidIngredientsBasicOrRecipeSize;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@AllArgsConstructor
@Builder(setterPrefix = "with")
@Jacksonized
@Getter
public class IngredientCreateDto {

    @NotBlank(message = "Name should be filled.")
    @Length(min = 2,max = 50,message = "Name length should be between {min} and {max} characters.")
    @ValidIngredientName
    private String name;

    @NotNull(message = "Price should be filled.")
    @Min(value = 0,message = "Price shouldn't be less than {value} .")
    private Integer price;

    @NotNull(message = "Loss probability should be filled.")
    @Range(min = 0,max = 100,message = "Loss probability should be between {min} and {max} value")
    private Short lossProbability;

    @ValidIngredientsBasicOrRecipeSize
    @ValidIfIngredientsExist
    @ValidIngredientFromIngredientsExist
    private List<Long> ingredientIds;

}
