package com.example.user_ingredient_service.service_unit;

import com.example.user_ingredient_service.dto.ingredient.IngredientCreateDto;
import com.example.user_ingredient_service.dto.ingredient.IngredientResponseDto;
import com.example.user_ingredient_service.entity.Ingredient;
import com.example.user_ingredient_service.exception.EntityNotExistException;
import com.example.user_ingredient_service.generator.IngredientDtoGeneratorUtil;
import com.example.user_ingredient_service.generator.IngredientGeneratorUtil;
import com.example.user_ingredient_service.mapper.IngredientMapper;
import com.example.user_ingredient_service.repository.IngredientRepository;
import com.example.user_ingredient_service.service.impl.IngredientServiceImpl;
import com.example.user_ingredient_service.service.impl.UserIngredientServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class IngredientServiceTest {

    @InjectMocks
    private IngredientServiceImpl ingredientService;
    @Mock
    private IngredientRepository ingredientRepository;
    @Mock
    private IngredientMapper ingredientMapper;
    @Mock
    private UserIngredientServiceImpl userIngredientService;
    private Ingredient ingredient;
    private IngredientCreateDto ingredientCreateDto;

    @BeforeEach
    void setup() {
        ingredient = IngredientGeneratorUtil.createValidIngredient();
        ingredient.setId(1L);
        ingredientCreateDto = IngredientDtoGeneratorUtil.createRecipeIngredientDto();
    }

    @Test
    void testCreateIngredientShouldSave() {
        given(ingredientMapper.fromDtoToEntity(ingredientCreateDto)).willReturn(ingredient);
        given(ingredientRepository.save(ingredient)).willReturn(ingredient);
        Assertions.assertDoesNotThrow(() -> ingredientService.createIngredient(ingredientCreateDto));
        Assertions.assertNotNull(ingredient);
    }

    @Test
    void testGetAllIngredientsShouldReturnAllIngredients() {
        Page<Ingredient> ingredients = new PageImpl<>(
                List.of(ingredient,
                        new Ingredient(),
                        new Ingredient(),
                        new Ingredient()));
        given(ingredientRepository.findAll(isA(Pageable.class))).willReturn(ingredients);
        Page<IngredientResponseDto> allIngredients = ingredientService.getAllIngredients(Pageable.unpaged());
        Assertions.assertNotNull(allIngredients);
        Assertions.assertEquals(4, allIngredients.getContent().size());
    }

    @Test
    void testGetIngredientByIdShouldReturnIngredient() {
        IngredientResponseDto responseDto = getResponseDto(ingredient);
        given(ingredientRepository.findById(1L)).willReturn(Optional.of(ingredient));
        given(ingredientMapper.fromEntityToDto(ingredient)).willReturn(responseDto);
        IngredientResponseDto ingredientById = ingredientService.getIngredientById(1L);
        Assertions.assertNotNull(ingredientById);
    }

    @Test
    void testGetIngredientByIdShouldThrowIngredientWithSuchIdNotExist() {
        given(ingredientRepository.findById(1L)).willReturn(Optional.empty());
        Assertions.assertThrows(EntityNotExistException.class,
                () -> ingredientService.getIngredientById(1L));
    }

    private IngredientResponseDto getResponseDto(Ingredient ingredient) {
        return IngredientResponseDto.builder().
                withPrice(ingredient.getPrice()).
                withName(ingredient.getName()).
                withLossProbability(ingredient.getLossProbability()).
                build();
    }

}
