package com.example.user_ingredient_service.mapper;


import com.example.user_ingredient_service.dto.TokenResponse;
import org.springframework.stereotype.Component;

@Component
public class TokenMapper {

    public TokenResponse mapTokens(String accessToken, String refreshToken) {
        return TokenResponse.builder()
                .withAccessToken(accessToken)
                .withRefreshToken(refreshToken)
                .build();
    }

}
