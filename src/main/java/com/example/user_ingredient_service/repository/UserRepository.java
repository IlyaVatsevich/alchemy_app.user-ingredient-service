package com.example.user_ingredient_service.repository;

import com.example.user_ingredient_service.entity.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UserRepository extends Neo4jRepository<User,Long> {}
