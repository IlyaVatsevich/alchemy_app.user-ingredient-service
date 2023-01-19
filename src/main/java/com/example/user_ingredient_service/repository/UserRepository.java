package com.example.user_ingredient_service.repository;

import com.example.user_ingredient_service.dto.HighScoreTable;
import com.example.user_ingredient_service.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.Optional;

public interface UserRepository extends Neo4jRepository<User,Long> {

    boolean existsByMail(String mail);

    boolean existsByLogin(String login);

    Optional<User> findByLogin(String login);

    @Query(value =
            """ 
            MATCH (u:User)
            WITH collect(u) as users
            UNWIND range(0, size(users)-1) as pos
            RETURN users[pos], users[pos].gold as score, 1 + pos as place
            ORDER BY score DESC SKIP $skip LIMIT $limit;
            """, countQuery = "MATCH (u:User) RETURN COUNT(u)" )
    Page<HighScoreTable> findUsersByMaxGold(Pageable pageable);

    @Query(value = """
            MATCH (u:User)-[r:OBTAIN]->(i:Ingredient)
            WITH collect(u) as users,i as ingr_count
            WHERE i.loss_probability > 0 AND i.price > 0
            UNWIND range(0,size(users)-1) as pos
            RETURN count(ingr_count) as score, users[pos],pos + 1 as place
            ORDER BY score DESC SKIP $skip LIMIT $limit
            """,countQuery = "MATCH (u:User) RETURN COUNT(u)")
    Page<HighScoreTable> findUsersByMaxUserIngredientCount(Pageable pageable);

}
