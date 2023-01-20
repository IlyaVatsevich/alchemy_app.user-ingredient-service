package com.example.user_ingredient_service.repository;

import com.example.user_ingredient_service.entity.Ingredient;
import com.example.user_ingredient_service.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.user_ingredient_service.generator.UserGeneratorUtil.createValidUser;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "spring.profiles.active = test")
@Testcontainers
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private Neo4jTemplate neo4jTemplate;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Container
    final static Neo4jContainer<?> container = new Neo4jContainer<>("neo4j:5")
            .withAdminPassword("secretSecret");

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.neo4j.uri",container::getBoltUrl);
        registry.add("spring.neo4j.authentication.username",()->"neo4j");
        registry.add("spring.neo4j.authentication.password",container::getAdminPassword);
    }

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
