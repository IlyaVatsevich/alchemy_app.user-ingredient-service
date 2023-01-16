package com.example.user_ingredient_service.repository;

import com.example.user_ingredient_service.entity.Ingredient;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.Set;

public interface IngredientRepository extends Neo4jRepository<Ingredient,Long> {

    @Query(value = "MATCH (i:Ingredient) WHERE i.price = 0 AND i.loss_probability = 0 RETURN i")
    Set<Ingredient> getAllBasicIngredients();

}
