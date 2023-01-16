package com.example.user_ingredient_service.repository;

import com.example.user_ingredient_service.entity.UserRole;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface UserRoleRepository extends Neo4jRepository<UserRole, Long> {

    @Query("MATCH (ur:UserRole) WHERE ur.role_name = 'USER' RETURN ur")
    UserRole getUserRoleUser();

    @Query("MATCH (ur:UserRole) WHERE ur.role_name = 'ADMIN' RETURN ur")
    UserRole getUserRoleAdmin();


}
