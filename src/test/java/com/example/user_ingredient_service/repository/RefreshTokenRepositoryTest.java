package com.example.user_ingredient_service.repository;

import com.example.user_ingredient_service.entity.RefreshToken;
import com.example.user_ingredient_service.entity.User;
import com.example.user_ingredient_service.test_container.Neo4jConfiguredContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.example.user_ingredient_service.generator.UserGeneratorUtil.createValidUser;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "spring.profiles.active = test")
@Transactional
class RefreshTokenRepositoryTest implements Neo4jConfiguredContainer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Neo4jTemplate neo4jTemplate;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

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
