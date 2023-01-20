package com.example.user_ingredient_service.repository;

import com.example.user_ingredient_service.entity.RefreshToken;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.Optional;

public interface RefreshTokenRepository extends Neo4jRepository<RefreshToken,Long> {

    Optional<RefreshToken> findByToken(String token);

    @Query(value = "MATCH (rt:RefreshToken)<-[ht:HAVE_TOKEN]-(u:User) WHERE ID(u) = $userId RETURN rt,ht,u;")
    Optional<RefreshToken> findByUserId(Long userId);

}
