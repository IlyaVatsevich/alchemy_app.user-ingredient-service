package com.example.user_ingredient_service.service_unit;

import com.example.user_ingredient_service.repository.IngredientRepository;
import com.example.user_ingredient_service.repository.UserRepository;
import lombok.Builder;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Consumer;

@Getter
@Builder
public class MockClass {

    private UserRepository userRepository;

    private IngredientRepository ingredientRepository;

    public void setupUserRepository(List<?> serviceClasses) {
        serviceClasses.forEach((Consumer<Object>) o -> {
            try {
                Field userRepoField = o.getClass().getDeclaredField("userRepository");
                userRepoField.setAccessible(true);
                userRepoField.set(o,userRepository);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }

        });
    }

    public void setupIngredientRepository(List<?> serviceClasses) {
        serviceClasses.forEach((Consumer<Object>) o -> {
            try {
                Field userRepoField = o.getClass().getDeclaredField("ingredientRepository");
                userRepoField.setAccessible(true);
                userRepoField.set(o,ingredientRepository);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }

}
