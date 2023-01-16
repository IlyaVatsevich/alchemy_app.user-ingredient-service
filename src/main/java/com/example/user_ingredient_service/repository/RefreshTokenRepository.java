package com.example.user_ingredient_service.repository;

import com.example.user_ingredient_service.entity.RefreshToken;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface RefreshTokenRepository extends Neo4jRepository<RefreshToken,Long> {
}
