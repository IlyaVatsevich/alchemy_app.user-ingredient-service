package com.example.user_ingredient_service.repository;

import com.example.user_ingredient_service.entity.RefreshToken;
import com.example.user_ingredient_service.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.example.user_ingredient_service.generator.UserGeneratorUtil.createValidUser;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "spring.profiles.active = test")
@Testcontainers
@Transactional
class RefreshTokenRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Neo4jTemplate neo4jTemplate;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Container
    final static Neo4jContainer<?> container = new Neo4jContainer<>("neo4j:5")
            .withAdminPassword("secretSecret");

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.neo4j.uri",container::getBoltUrl);
        registry.add("spring.neo4j.authentication.username",()->"neo4j");
        registry.add("spring.neo4j.authentication.password",container::getAdminPassword);
    }
    
    @Test
    void testSaveRefreshTokenShouldSaveRefreshToken () {
        User validUser = createValidUser();
        User savedUser = userRepository.save(validUser);
        RefreshToken refreshToken = buildRefreshToken(savedUser);
        RefreshToken savedRefreshToken = refreshTokenRepository.save(refreshToken);
        assertNotNull(savedRefreshToken.getId());
        assertEquals(1,neo4jTemplate.count(RefreshToken.class));
    }
    
    private RefreshToken buildRefreshToken(User user) {
        return RefreshToken.builder()
                .withToken(UUID.randomUUID().toString())
                .withUser(user)
                .withExpiryDate(LocalDateTime.now())
                .build();
    }


}
