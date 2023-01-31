package com.example.user_ingredient_service.repository;

import com.example.user_ingredient_service.entity.Ingredient;
import com.example.user_ingredient_service.entity.User;
import com.example.user_ingredient_service.mock.MockKafka;
import com.example.user_ingredient_service.test_container.Neo4jConfiguredContainer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.user_ingredient_service.generator.UserGeneratorUtil.createValidUser;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"spring.profiles.active = test",
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration"})
@Transactional
@ExtendWith(Neo4jConfiguredContainer.class)
class UserRepositoryTest extends MockKafka {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private Neo4jTemplate neo4jTemplate;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    void testUserSaveShouldSaveValidUser() {
        User validUser = createValidUser();
        validUser.setRoles(Set.of(userRoleRepository.getUserRoleUser()));
        User savedUser = userRepository.save(validUser);
        assertNotNull(savedUser.getId());
        assertEquals(3,neo4jTemplate.count(User.class));
        assertEquals(1,savedUser.getRoles().size());
    }

    @Test
    void testUserShouldSaveWithIngredients() {
        User validUser = createValidUser();
        validUser.setUserIngredients(userIngredients());
        User savedUser = userRepository.save(validUser);
        assertEquals(4,savedUser.getUserIngredients().size());
    }

    private List<User.UserIngredient> userIngredients() {
        Set<Ingredient> ingredients = ingredientRepository.getAllBasicIngredients();
        return ingredients.stream()
                .map(this::userIngredient)
                .collect(Collectors.toUnmodifiableList());
    }

    private User.UserIngredient userIngredient(Ingredient ingredient) {
        return User.UserIngredient.builder()
                .withIngredient(ingredient)
                .withCount(1L)
                .build();
    }

}
