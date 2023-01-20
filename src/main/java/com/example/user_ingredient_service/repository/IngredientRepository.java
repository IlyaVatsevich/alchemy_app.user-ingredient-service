package com.example.user_ingredient_service.repository;

import com.example.user_ingredient_service.entity.Ingredient;
import com.example.user_ingredient_service.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IngredientRepository extends Neo4jRepository<Ingredient,Long> {

    @Query(value = "MATCH (i:Ingredient) WHERE i.price = 0 AND i.loss_probability = 0 RETURN i")
    Set<Ingredient> getAllBasicIngredients();

    boolean existsByName(String name);

    @Query(value = """
            MATCH (i1:Ingredient)-[r:CREATED_FROM]->(i:Ingredient)
            WHERE ID(i) IN $ingredientIds
            WITH count(r) as cr,i1 as ingr
            WHERE cr = $ingredientCount
            RETURN ingr;
            """)
    Optional<Ingredient> findIngredientByIngredientsIds(List<Long> ingredientIds, Long ingredientCount);

    @Query(value = """
            MATCH (u:User)-[o:OBTAIN]->(i:Ingredient)
            WITH collect(i) as ingrs,i as ingr_count,u as user
            WHERE ID(user) = $userId
            UNWIND range(0,size(ingrs)-1) as pos
            RETURN ingrs[pos] as ingredient,count(ingr_count) as count
            :#{orderBy(#pageable)} SKIP $skip LIMIT $limit
            """,
            countQuery = "MATCH (u:User)-[o:OBTAIN]->() WHERE ID(u) = $userId RETURN count(o)" )
    Page<User.UserIngredient> findUserIngredientByUserId(Long userId, Pageable pageable);

}
