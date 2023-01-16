package com.example.user_ingredient_service.repository;

import com.example.user_ingredient_service.entity.Ingredient;
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

import static com.example.user_ingredient_service.generator.IngredientGeneratorUtil.createValidIngredient;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "spring.profiles.active = test")
@Testcontainers
@Transactional
class IngredientRepositoryTest {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private Neo4jTemplate neo4jTemplate;

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
    void testIngredientSaveShouldSaveValidIngredient() {
        Ingredient validIngredient = createValidIngredient();
        Ingredient savedIngredient = ingredientRepository.save(validIngredient);
        assertNotNull(savedIngredient.getId());
        assertEquals(5,neo4jTemplate.count(Ingredient.class));
    }

}
