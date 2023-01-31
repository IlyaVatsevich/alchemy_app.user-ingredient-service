package com.example.user_ingredient_service.test_container;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.Neo4jContainer;

public class Neo4jConfiguredContainer implements BeforeAllCallback {

    private static final Neo4jContainer<?> container = new Neo4jContainer<>("neo4j:5")
            .withAdminPassword("secretSecret");

    @Override
    public void beforeAll(ExtensionContext context) {
        neo4jProperties();
    }

    private static void neo4jProperties() {
        container.start();
        System.setProperty("spring.neo4j.uri",container.getBoltUrl());
        System.setProperty("spring.neo4j.authentication.username","neo4j");
        System.setProperty("spring.neo4j.authentication.password",container.getAdminPassword());
    }
}
