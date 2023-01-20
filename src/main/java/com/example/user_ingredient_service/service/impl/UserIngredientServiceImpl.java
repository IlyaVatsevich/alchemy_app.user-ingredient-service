package com.example.user_ingredient_service.service.impl;

import com.example.user_ingredient_service.dto.UserIngredientDto;
import com.example.user_ingredient_service.entity.Ingredient;
import com.example.user_ingredient_service.entity.User;
import com.example.user_ingredient_service.mapper.UserIngredientMapper;
import com.example.user_ingredient_service.repository.IngredientRepository;
import com.example.user_ingredient_service.repository.UserRepository;
import com.example.user_ingredient_service.security.UserHolder;
import com.example.user_ingredient_service.service.UserIngredientService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@DependsOn("liquibase")
public class UserIngredientServiceImpl implements UserIngredientService {

    private final UserRepository userRepository;
    private final IngredientRepository ingredientRepository;
    private final UserIngredientMapper userIngredientMapper;
    private final UserHolder userHolder;
    private Set<Ingredient> basicIngredients;

    @PostConstruct
    void getBasicIngredients() {
        basicIngredients = ingredientRepository.getAllBasicIngredients();
    }

    @Override
    public void addNewBasicIngredientToUsers(Ingredient ingredient) {
        basicIngredients.add(ingredient);
        List<User> users = userRepository.findAll()
                .stream()
                .map(user -> {
                    List<User.UserIngredient> userIngredients = user.getUserIngredients();
                    userIngredients.add(userIngredientMapper.buildUserIngredient(ingredient));
                    user.setUserIngredients(userIngredients);
                    return user;})
                .toList();
        userRepository.saveAll(users);
        log.info("Basic ingredient with id: " + ingredient.getId() + ", added to all user bags, successfully." );
    }

    @Override
    public void initializeUserIngredient(User newUser) {
        newUser.setUserIngredients(userIngredientMapper.buildUserIngredients(basicIngredients));
        log.info("Inventory of user - {}  is initialized",newUser.getId());
    }

    @Override
    public Page<UserIngredientDto> getUserIngredient(Pageable pageable) {
        Long userId = userHolder.getUser().getId();
        return ingredientRepository.findUserIngredientByUserId(userId,pageable)
                .map(userIngredientMapper::userIngredientToUserIngredientDto);
    }
}
