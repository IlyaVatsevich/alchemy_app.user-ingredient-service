package com.example.user_ingredient_service.repository;

import com.example.user_ingredient_service.entity.Ingredient;
import com.example.user_ingredient_service.mock.MockKafka;
import com.example.user_ingredient_service.test_container.Neo4jConfiguredContainer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.transaction.annotation.Transactional;

import static com.example.user_ingredient_service.generator.IngredientGeneratorUtil.createValidIngredient;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"spring.profiles.active = test",
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration"})
@Transactional
@ExtendWith(Neo4jConfiguredContainer.class)
class IngredientRepositoryTest extends MockKafka {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private Neo4jTemplate neo4jTemplate;

    @Test
    void testIngredientSaveShouldSaveValidIngredient() {
        Ingredient validIngredient = createValidIngredient();
        Ingredient savedIngredient = ingredientRepository.save(validIngredient);
        assertNotNull(savedIngredient.getId());
        assertEquals(5,neo4jTemplate.count(Ingredient.class));
    }

}
