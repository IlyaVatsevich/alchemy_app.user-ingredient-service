package com.example.user_ingredient_service.mapper;

import com.example.user_ingredient_service.entity.RefreshToken;
import com.example.user_ingredient_service.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Component
public class RefreshTokenMapper {

    @Value("${security-custom.refresh-token.expiration}")
    private long expireDate;

    public RefreshToken buildRefreshToken(User user) {
        return RefreshToken.builder().
                withToken(generateToken()).
                withUser(user).
                withExpiryDate(mapExpireDate()).
                build();
    }

    private LocalDateTime mapExpireDate() {
        return LocalDateTime.ofInstant(Instant.now().plusMillis(expireDate), ZoneId.systemDefault());
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

}
