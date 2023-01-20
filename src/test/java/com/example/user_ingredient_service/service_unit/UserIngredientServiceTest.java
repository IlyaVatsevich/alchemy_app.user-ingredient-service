package com.example.user_ingredient_service.service_unit;

import com.example.user_ingredient_service.entity.Ingredient;
import com.example.user_ingredient_service.entity.User;
import com.example.user_ingredient_service.generator.IngredientGeneratorUtil;
import com.example.user_ingredient_service.generator.UserGeneratorUtil;
import com.example.user_ingredient_service.generator.UserIngredientGeneratorUtil;
import com.example.user_ingredient_service.mapper.UserIngredientMapper;
import com.example.user_ingredient_service.repository.IngredientRepository;
import com.example.user_ingredient_service.repository.UserRepository;
import com.example.user_ingredient_service.service.impl.UserIngredientServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserIngredientServiceTest {

    @InjectMocks
    private UserIngredientServiceImpl userIngredientService;

    @Mock
    private UserIngredientMapper userIngredientMapper;

    private final MockClass mockClass = MockClass.builder()
            .userRepository(mock(UserRepository.class))
            .ingredientRepository(mock(IngredientRepository.class))
            .build();

    private Ingredient ingredient;

    private User user;

    private User.UserIngredient userIngredient;

    @BeforeEach
    void setMocks() {
        List<UserIngredientServiceImpl> services = List.of(userIngredientService);
        mockClass.setupIngredientRepository(services);
        mockClass.setupUserRepository(services);
    }


    @BeforeEach
    void setup() throws Exception {
        ingredient = IngredientGeneratorUtil.createValidIngredient();
        ingredient.setId(1L);
        user = UserGeneratorUtil.createValidUser();
        user.setId(1L);
        userIngredient = UserIngredientGeneratorUtil.createUserIngredient(ingredient,1L);
        Class<? extends UserIngredientServiceImpl> userIngredientServiceClass = userIngredientService.getClass();
        Field basicIngredients = userIngredientServiceClass.getDeclaredField("basicIngredients");
        basicIngredients.setAccessible(true);
        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(ingredient);
        basicIngredients.set(userIngredientService,ingredients);
    }

    @Test
    void testAddNewBasicIngredientToUsersShouldSaveAll() {
        List<User.UserIngredient> userIngredients = new ArrayList<>();
        userIngredients.add(userIngredient);
        user.setUserIngredients(userIngredients);
        List<User> users = new ArrayList<>();
        users.add(user);
        when(mockClass.getUserRepository().findAll()).thenReturn(users);
        when(userIngredientMapper.buildUserIngredient(ingredient)).thenReturn(userIngredient);
        when(mockClass.getUserRepository().saveAll(users)).thenReturn(users);
        Assertions.assertDoesNotThrow(() -> userIngredientService.addNewBasicIngredientToUsers(ingredient));
    }

    @Test
    void testInitializeUserIngredientShouldSaveAll(){
        Assertions.assertDoesNotThrow(()->userIngredientService.initializeUserIngredient(user));
    }

}
