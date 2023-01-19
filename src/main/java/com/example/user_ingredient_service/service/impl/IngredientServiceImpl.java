package com.example.user_ingredient_service.service.impl;

import com.example.user_ingredient_service.dto.ingredient.IngredientCreateDto;
import com.example.user_ingredient_service.dto.ingredient.IngredientResponseDto;
import com.example.user_ingredient_service.entity.Ingredient;
import com.example.user_ingredient_service.exception.EntityNotExistException;
import com.example.user_ingredient_service.mapper.IngredientMapper;
import com.example.user_ingredient_service.repository.IngredientRepository;
import com.example.user_ingredient_service.service.IngredientService;
import com.example.user_ingredient_service.service.UserIngredientService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;
    private final UserIngredientService userIngredientService;

    @Override
    @Transactional
    public void createIngredient(IngredientCreateDto ingredientDto) {
        Ingredient newIngredient;
        if (ingredientDto.getIngredientIds() == null || ingredientDto.getIngredientIds().isEmpty()) {
            newIngredient = ingredientRepository.save(newBasicIngredient(ingredientDto));
        } else {
            newIngredient = ingredientRepository.save(newRecipeIngredient(ingredientDto));
        }
        userIngredientService.addNewBasicIngredientToUsers(newIngredient);
        log.info("New ingredient with id: {} saved successfully.",newIngredient.getId());
    }

    @Override
    public Page<IngredientResponseDto> getAllIngredients(Pageable pageable) {
        return ingredientRepository.findAll(pageable).map(ingredientMapper::fromEntityToDto);
    }

    @Override
    public IngredientResponseDto getIngredientById(Long id) {
        return ingredientMapper.fromEntityToDto(ingredientRepository.findById(id)
                .orElseThrow(()->new EntityNotExistException("Ingredient with such id: " + id + ", not exist.")));
    }

    private Ingredient newBasicIngredient(IngredientCreateDto ingredientDto) {
        if (ingredientDto.getPrice()!=0 || ingredientDto.getLossProbability()!=0) {
            throw new ValidationException("Loss probability and price of basic ingredient must be equal 0.");
        }
        return ingredientMapper.fromDtoToEntity(ingredientDto);
    }

    private Ingredient newRecipeIngredient(IngredientCreateDto ingredientDto) {
        List<Ingredient> ingredientsFromIngredientCreated = ingredientRepository.findAllById(ingredientDto.getIngredientIds());
        Ingredient newRecipeIngredient = ingredientMapper.fromDtoToEntity(ingredientDto);
        List<Ingredient.IngredientsCreatedFrom> ingredientsCreatedFrom =
                ingredientMapper.mapIngredientsToIngredientsCreatedFrom(ingredientsFromIngredientCreated);
        newRecipeIngredient.setIngredients(ingredientsCreatedFrom);
        return newRecipeIngredient;
    }
}
