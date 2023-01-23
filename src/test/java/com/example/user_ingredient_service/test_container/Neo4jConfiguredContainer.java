package com.example.user_ingredient_service.test_container;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public interface Neo4jConfiguredContainer {

    @Container
    Neo4jContainer<?> container = new Neo4jContainer<>("neo4j:5")
            .withAdminPassword("secretSecret");

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.neo4j.uri",container::getBoltUrl);
        registry.add("spring.neo4j.authentication.username",()->"neo4j");
        registry.add("spring.neo4j.authentication.password",container::getAdminPassword);
    }

}
